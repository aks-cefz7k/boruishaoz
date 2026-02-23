package com.openatc.agent.gateway;

import com.google.gson.Gson;
import com.openatc.agent.model.MyWebSocketServer;
import com.openatc.agent.model.WebSocketSub;
import io.lettuce.core.RedisException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author laoxia
 * @Date 2021/7/28 10:20
 * @Describetion
 */
@ServerEndpoint(value = "/ws")
@Component
@Data
public class WebSocketServer {

    private Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @Autowired
    private RedisTemplate redisTemplate;


    private String EventAgentFault = "event/agentfault"; //设备故障消息类型
    private String StatusPattern = "status/pattern"; //方案相位消息类型
    private String StatusChannel = "status/channel"; //设备通道信息
    private String EventTrafficData = "event/trafficdata"; //交通事件


    //当前在线总数
    private volatile static int onlineCount = 0;

    //Session对象集合
    public final static List<WebSocketServer> webSocketSet = new CopyOnWriteArrayList();

    private final static Map<Session, MyWebSocketServer> patternWebSocketSet = new ConcurrentHashMap<>();
    private final static Map<Session, MyWebSocketServer> trafficIncidentWebSocketSet = new ConcurrentHashMap<>();
    private final static Map<Session, MyWebSocketServer> faultIncidentWebSocketSet = new ConcurrentHashMap<>();


    // 此处存放Spring boot创建的RedisTemplate
    public static WebSocketServer webSocketComponent;

    /**
     * 存放各个消息类型的agentIds，后面用于过滤
     */
    private Map<String, Set<String>> infoAndAgentIds = new ConcurrentHashMap<>();

    /**
     * 保存相同的一个topic被多少个不同的客户端订阅
     * 不同的客户端可能订阅同一topic
     * 要保证某一客户端取消后 其他客户端依然能够收到订阅的消息
     */
    private final static Map<String, Set<Session>> AgentIdAndNum = new ConcurrentHashMap<>();


    public static void setWebSocketComponent(WebSocketServer webSocketComponent) {
        WebSocketServer.webSocketComponent = webSocketComponent;
    }

    //与客户端的连接会话
    private Session session;

    /**
     * 下面是各个WebSocket存储订阅消息的列表，订阅的消息分为两种形式，轮询消息和通道消息
     * 轮询消息：通过Redis的KV保存，需后台主动发起定时轮询请求，来获取最新数据
     * 通道消息：通过Redis的Channel接收最新消息，由设备端主动发起，后台只需等待接受
     */

    // 通道列表,保存订阅的频道
    public Set<String> channelTypeDataSet = new HashSet<>();
    // 轮询列表，保存订阅的频道
    public Map<String, Set<String>> pollingTypeMap = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void checkRedis() {
        setWebSocketComponent(this);
    }

    // 发送帧数据的频率（减慢的倍数）
    public int frameSendRare = 0;

    // 发送帧数据的计数
    public int frameSendIndex = 0;

    // 接受username
    private String username = "kissAdmin";

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.session.setMaxIdleTimeout(5 * 60 * 1000);
        this.session.setMaxTextMessageBufferSize(5 * 1024 * 1024);
        patternWebSocketSet.put(session, new MyWebSocketServer(username, this));
        synchronized (WebSocketServer.class) {
            onlineCount++;
            log.info("onOpen URL:{},Session ID:{},OnlineCount:{}", session.getRequestURI(), session.getId(), onlineCount);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("onMessage sessionId:{},receive:{}", session.getId(), message);
        Gson gs = new Gson();
        WebSocketSub wss;
        wss = gs.fromJson(message, WebSocketSub.class);
        String infoType = wss.getInfotype().trim(); //订阅类型
        String subscribe = wss.getSubscribe(); //开始or结束标
        String[] para = wss.getParam(); //订阅的通道
        String model = wss.getModel().trim();
        setInfoAndAgentIds(para, infoType, subscribe);
        log.info(this.infoAndAgentIds.toString());
        if (infoType.isEmpty() || subscribe.isEmpty()) {
            log.info("onMessage error: format empty");
            return;
        }
        // 控制消息
        if (infoType.contains("control")) {
            if (infoType.equals("control/agentframe")) {
                if (subscribe.equals("rate")) {
                    String str = para[0];
                    frameSendRare = Integer.parseInt(str);
                    if (frameSendRare < 0)
                        frameSendRare = 0;
                    log.info("Control, sessionId:{},Set frameSendRare:{}", session.getId(), frameSendRare);
                }
            }
        } else if ("up".equals(subscribe)) {//开启订阅消息
            // 拥堵事件订阅消息
            if (EventTrafficData.equals(infoType)) {
                if (trafficIncidentWebSocketSet.size() == 0)
                    webSocketComponent.redisService.subsMessage(model + ":" + infoType);
                trafficIncidentWebSocketSet.put(session, new MyWebSocketServer(username, this));
            }
            // 故障事件订阅消息
            if (EventAgentFault.equals(infoType)) {
                //set集合不为空则表明已经订阅
                if (faultIncidentWebSocketSet.size() == 0)
                    webSocketComponent.redisService.subsMessage(model + ":" + infoType);
                faultIncidentWebSocketSet.put(session, new MyWebSocketServer(username, this));
            } else if (isPollingType(infoType)) {//messageType含有pattern就不是轮询
                //添加监听记录映射
                for (int i = 0; i < para.length; i++) {
                    String subsChannel = model + ":" + infoType + ":" + para[i];
                    Set<String> edgeSet = pollingTypeMap.get(infoType);
                    if (edgeSet == null) {
                        edgeSet = new HashSet<>();
                        pollingTypeMap.put(infoType, edgeSet);
                    }
                    edgeSet.add(subsChannel);
                }
                log.info("Polling Start! One Message,sessionId:{},messageType:{}", session.getId(), infoType);
                try {
                    RedisService redisService = webSocketComponent.redisService;
                    redisService.sendMessageToSubsClients(infoType);
                } catch (Exception e) {
                    log.info("RedisConnectionException: Unable to connect to redis:6379");
                    return;
                }
            } else {
                for (int i = 0; i < para.length; i++) {
                    // 判断此消息类型是否已监听，没有的添加监听,   然后加入Set
                    String subsChannel = model + ":" + infoType + ":" + para[i].trim();
                    try {
                        webSocketComponent.redisService.subsMessage(subsChannel);
                    } catch (RedisException e) {
                        log.info("SubsMessage failed to connect to redis");
                    }
                    channelTypeDataSet.add(subsChannel);
                    if (!AgentIdAndNum.containsKey(subsChannel)) {
                        Set<Session> set = new HashSet<>();
                        set.add(session);
                        AgentIdAndNum.put(subsChannel, set);
                    } else {
                        Set<Session> set = AgentIdAndNum.get(subsChannel);
                        set.add(session);
                        AgentIdAndNum.put(subsChannel, set);
                    }
                }
            }
        } else if ("down".equals(subscribe)) {//结束消息订阅
            // 事件消息
            if (EventTrafficData.equals(infoType)) {
                if (trafficIncidentWebSocketSet.size() == 1)
                    webSocketComponent.redisService.unSubsMessage(model + ":" + infoType);
                trafficIncidentWebSocketSet.remove(session);
            }
            //事故消息
            if (EventAgentFault.equals(infoType)) {
                //当set集合里面没有元素时结束当前订阅
                if (faultIncidentWebSocketSet.size() == 1)
                    webSocketComponent.redisService.unSubsMessage(model + ":" + infoType);
                faultIncidentWebSocketSet.remove(session);
            }
            // 定时器订
            if (isPollingType(infoType)) {
                for (int i = 0; i < para.length; i++) {
                    String subsChannel = model + ":" + infoType + ":" + para[i];
                    Set<String> edgeSet = pollingTypeMap.get(infoType);
                    if (edgeSet != null) {
                        edgeSet.remove(subsChannel);
                        if (edgeSet.size() == 0)
                            pollingTypeMap.remove(infoType);
                    }
                }
            }
            // Channel部分
            else {
                for (int i = 0; i < para.length; i++) {
                    String subsChannel = model + ":" + infoType + ":" + para[i];
                    if (AgentIdAndNum.get(subsChannel).size() == 1) {
                        webSocketComponent.redisService.unSubsMessage(subsChannel);
                        channelTypeDataSet.remove(subsChannel);
                        AgentIdAndNum.remove(subsChannel);
                    } else {
                        Set<Session> set = AgentIdAndNum.get(subsChannel);
                        set.remove(session);
                        AgentIdAndNum.put(subsChannel, set);
                    }
                }
            }
            // 打印信息
            log.info("SessionID:" + session.getId() + ",###subscribe types###: " + channelTypeDataSet.toString());
            for (Map.Entry<String, Set<String>> entry : pollingTypeMap.entrySet()) {
                log.info("SessionID:" + session.getId() + ",***polling types***: " + entry.getKey() + ",polling values:" + entry.getValue());
            }
        } else {
            log.info("Subscribe type error!");
        }
    }

    @OnError
    public void onError(Throwable error) throws IOException {
        log.error("onError:{}，Session ID:{}", error.toString(), session.getId());
        session.close();
    }


    @OnClose
    public void onClose(Session session, CloseReason reason) {
        Set<String> tempSet = new HashSet<>(channelTypeDataSet);
        channelTypeDataSet.clear();
        // 取消Channel订阅
        try {
            for (String subsChannel : tempSet) {
                //如果还有其他客户端在订阅则不取消这个topic的订阅
                if (AgentIdAndNum.containsKey(subsChannel) && AgentIdAndNum.get(subsChannel).size() == 1 && AgentIdAndNum.get(subsChannel).contains(session)) {
                    webSocketComponent.redisService.unSubsMessage(subsChannel);
                }
            }
        } catch (RedisException e) {
            log.info("unSubsMessage failed to connect to redis");
        } finally {
            tempSet.clear();
            patternWebSocketSet.remove(session);
            trafficIncidentWebSocketSet.remove(session);
            faultIncidentWebSocketSet.remove(session);
            synchronized (WebSocketServer.class) {
                onlineCount--;
                log.info("onClose:ID: {}, {}, OnlineCount：{}", session.getId(), reason.toString(), onlineCount);
            }
        }
    }


    /**
     * 整理前端发送的数据集合
     *
     * @return
     */
    public void setInfoAndAgentIds(String[] para, String infoType, String subscribe) {
        Set<String> agentIds = this.infoAndAgentIds.get(infoType.trim());
        if (agentIds == null) agentIds = new HashSet<>();
        for (String value : para) {
            if (value == null) continue;
            String agentId = value.trim();
            if (subscribe.equals("up")) {
                agentIds.add(agentId);
            } else if (subscribe.equals("down")) {
                agentIds.remove(agentId);
            }
        }
        this.infoAndAgentIds.put(infoType.trim(), agentIds);
    }


    /**
     * 判断是否是轮询消息
     *
     * @param subsChannel:消息类型，管道、轮询
     * @return boolean
     */
    boolean isPollingType(String subsChannel) {
        if (subsChannel.contains("status/pattern"))
            return false;
        else
            return true;
    }


    /**
     * 发送消息
     *
     * @param message
     */
    public synchronized void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("sendMessage Error:{},session ID:{}", e.getMessage(), session.getId());
        }
    }


    public static Map<Session, MyWebSocketServer> getPatternWebSocketSet() {
        return patternWebSocketSet;
    }

    public static Map<Session, MyWebSocketServer> getTrafficIncidentWebSocketSet() {
        return trafficIncidentWebSocketSet;
    }

    public static Map<Session, MyWebSocketServer> getFaultIncidentWebSocketSet() {
        return faultIncidentWebSocketSet;
    }

}
