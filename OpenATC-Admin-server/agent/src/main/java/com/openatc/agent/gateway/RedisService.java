package com.openatc.agent.gateway;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.openatc.agent.model.MyWebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.*;

/**
 * @author laoxia
 * @Date 2021/7/28 13:02
 * @Describetion
 */
@Component
public class RedisService {

    private Logger log = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private MessageListenerAdapter listenerAdapter;


    Map<Session, MyWebSocketServer> patternWebSocketSet = WebSocketServer.getPatternWebSocketSet();

    // 接受Redis订阅的消息
    public void receiveSubsMessage(String message, String type) {
        JSONObject pattern = JSONObject.parseObject(message);
        String agentId = pattern.getString("thirdpartyid");
        //如果不是asc:status/pattern，则退出
        if (!type.contains("asc:status/pattern")) return;
        log.info("receive pattern message: " + message);
        // 如果没有客户端订阅，取消Redis监听
        if (patternWebSocketSet.size() == 0) {
            unSubsMessage(null);
        } else {
            for (Session session : patternWebSocketSet.keySet()) {
                WebSocketServer webSocketServer = patternWebSocketSet.get(session).getWebSocketServer();
                Set<String> agentIds = webSocketServer.getInfoAndAgentIds().get("status/pattern");
                if (agentIds != null && agentIds.contains(agentId)) {
                    webSocketServer.sendMessage(message);
                }
            }
        }
    }


    public void subsMessage(String message) {
        for (Session session : patternWebSocketSet.keySet()) {
            if (patternWebSocketSet.get(session).getWebSocketServer().channelTypeDataSet.contains(message))
                return;
        }
        synchronized (this) {
            container.addMessageListener(listenerAdapter, new PatternTopic(message));
        }
        log.info("SubsMessage: {}", message);
    }

    // 取消Redis订阅的消息，如果message为null，关闭监听链路
    public void unSubsMessage(String message) {
        synchronized (this) {
            if (message == null) {
                container.removeMessageListener(listenerAdapter);
            } else {
                container.removeMessageListener(listenerAdapter, new PatternTopic(message));
            }
        }
        log.info("UnSubsMessage: {}", message);
    }

    //发送消息给订阅的客户端
    public void sendMessageToSubsClients(String type) {
        Map<Session, MyWebSocketServer> fwss = WebSocketServer.getPatternWebSocketSet();
        for (Session session : fwss.keySet()) {
            sendValuesMessage(type, fwss.get(session).getWebSocketServer());
        }
    }

    private boolean sendValuesMessage(String type, WebSocketServer webSock) {
        Collection<String> set = webSock.pollingTypeMap.get(type);
        return sendMessageToClient(set, type, webSock);
    }


    private boolean sendMessageToClient(Collection<String> keySet, String type, WebSocketServer webSock) {
        if (keySet != null && keySet.size() != 0) {
            //获取redis数据
            StringBuffer sendBuffer = appendAllValueFromRedis(keySet);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("infotype", type);
            jsonObject.addProperty("data", sendBuffer.toString());
            // 数据获取完，发送给客户端
            if (sendBuffer != null && sendBuffer.length() > 2) {
                log.info("send message: " + jsonObject.toString());
                webSock.sendMessage(jsonObject.toString());
            }
            return true;
        } else
            return false;
    }


    // 当此数值等于零，则没有客户端订阅帧数据，增加等待时间
    int pollingFrameSleepIndex = 0;

    //写入到配置文件中，方便修改
    private int frameDataRateTemp = 1;

    //创建cMap，用于存放redis中的帧数据，中间件
    private Map<String, String> cMap = new HashMap<>();

    /**
     * 定时任务
     * 定时轮询获取消息
     */
    @Scheduled(fixedRate = 100)
    protected void getAllPollingFrameData() throws InterruptedException {
        Map<Session, MyWebSocketServer> fwss = WebSocketServer.getPatternWebSocketSet();
        if (pollingFrameSleepIndex == 0) {
            Thread.sleep(500);
        }
        //存放数据之前先清空之前的数据
        cMap.clear();
        /**
         * 有一个中间件map,用于存放redis的数据
         * 首先我们要知道将哪些redis的数据读取出来存放到map中
         * 遍历客户端集合
         * 所有客户端所请求的set的并集就是需要存放到map中的数据。
         */
        Set<String> allClientsRequireSet = new HashSet<>();
        for (Session session : fwss.keySet()) {
            String type = "agentframe";
            Set<String> myClientRequireSet = fwss.get(session).getWebSocketServer().getPollingTypeMap().get(type);
            if (myClientRequireSet != null && myClientRequireSet.size() > 0) {
                for (String str : myClientRequireSet) {
                    allClientsRequireSet.add(str);
                }
            }
        }
        //向cMap中添加数据
        if (allClientsRequireSet != null && allClientsRequireSet.size() > 0) {
            for (String str : allClientsRequireSet) {
                String value = redisTemplate.opsForValue().get(str);
                cMap.put(str, value);
            }
        } else {
            return;
        }
        pollingFrameSleepIndex = 0;

        for (Session session : fwss.keySet()) {
            String name = patternWebSocketSet.get(session).getName();

            //根据各个客户端的请求频率发送帧数据
            String type = "agentframe";
            Collection<String> set = fwss.get(session).getWebSocketServer().pollingTypeMap.get(type);
            if (set != null && set.size() > 0) {
                pollingFrameSleepIndex++;
            } else {
                continue;
            }
            if (fwss.get(session).getWebSocketServer().getFrameSendRare() * frameDataRateTemp < fwss.get(session).getWebSocketServer().frameSendIndex) {
                if (sendMessageToClient(set, type, fwss.get(session).getWebSocketServer())) {
                    fwss.get(session).getWebSocketServer().frameSendIndex = 0;
                }
            } else {
                fwss.get(session).getWebSocketServer().frameSendIndex++;
            }
        }
    }


    // 把Redis中的value组合成一个json的字符串
    private StringBuffer appendAllValueFromRedis(Collection<String> keySet) {
        int i = 0;// 当前计数器
//        int count = keySet.size();
        keySet.size();
        StringBuffer sendBuffer = new StringBuffer("[");

        for (String value : keySet) {
            String strFrame = null;
            try {
                if (value.equals("asc:agentframe:network")) {
                    strFrame = cMap.get(value);
                } else {
                    strFrame = redisTemplate.opsForValue().get(value);
                }
            } catch (Exception e) {
                log.info("RedisConnectionException: Unable to connect to redis:6379");
                sendBuffer = new StringBuffer();
                return sendBuffer;
            }

            if (strFrame == null || strFrame.isEmpty() || strFrame.length() < 3)
                continue;

            // 第一个不用加逗号
            if (i != 0)
                sendBuffer.append(",");

            sendBuffer.append(strFrame);
            i++;
        }
        sendBuffer.append("]");
        return sendBuffer;
    }
}
