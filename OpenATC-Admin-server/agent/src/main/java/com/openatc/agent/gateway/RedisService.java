package com.openatc.agent.gateway;

import com.google.gson.Gson;
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

    Gson gson = new Gson();


    Map<Session, MyWebSocketServer> patternWebSocketSet = WebSocketServer.getPatternWebSocketSet();
    Map<Session, MyWebSocketServer> faultIncidentWebSocketSet = WebSocketServer.getFaultIncidentWebSocketSet();
    Map<Session, MyWebSocketServer> trafficIncidentWebSocketSet = WebSocketServer.getTrafficIncidentWebSocketSet();

    // 接受Redis订阅的消息
    public void receiveSubsMessage(String message, String type) {

        //如果没有客户端订阅，取消Redis监听
        if (patternWebSocketSet.size() == 0) {
            unSubsMessage(null);
            return;
        }

        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String agentId = jsonObject.get("agentid").getAsString();

//        log.info("receive " + type + " message: " + message);
        if (type.contains("asc:status/pattern")) {
            for (Session session : patternWebSocketSet.keySet()) {
                WebSocketServer webSocketServer = patternWebSocketSet.get(session).getWebSocketServer();
                Set<String> agentIds = webSocketServer.getInfoAndAgentIds().get("status/pattern");
                if (agentIds != null && agentIds.contains(agentId)) {
                    webSocketServer.sendMessage(message);
                }
            }
        }

        if (type.contains("asc:status/fault")) {
            for (Session session : faultIncidentWebSocketSet.keySet()) {
                faultIncidentWebSocketSet.get(session).getWebSocketServer().sendMessage(message);
            }
        }

        if ("asc:event/trafficdata".equals(type)) {
            for (Session session : trafficIncidentWebSocketSet.keySet()) {
                trafficIncidentWebSocketSet.get(session).getWebSocketServer().sendMessage(message);
            }
        }
    }


    // 定时读取设备统计数据
    @Scheduled(fixedRate = 60000)
    private void getStatisticsDataFromRedis() {
        for (Session session : patternWebSocketSet.keySet()) {
            WebSocketServer webSocketServer = patternWebSocketSet.get(session).getWebSocketServer();
            // 发送路口交通强度
            sendValuesMessage("status/intensity", webSocketServer.getInfoAndAgentIds().get("status/intensity"), webSocketServer);

            // 发送设备统计信息
            sendValuesMessage("status/fso", webSocketServer.getInfoAndAgentIds().get("status/fso"), webSocketServer);

            // 发送路网交通数据
            sendValuesMessage("status/netdata", webSocketServer);

            // 发送设备总体统计数据
            sendValuesMessage("status/snapshot", webSocketServer.getInfoAndAgentIds().get("status/snapshot"), webSocketServer);

            // 发送总体方案数据
            sendValuesMessage("status/keypattern", webSocketServer.getInfoAndAgentIds().get("status/keypattern"), webSocketServer);
        }
    }


    //订阅topic
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

    public boolean sendValuesMessage(String type, Set deviceIds, WebSocketServer webSock) {
        Collection<String> set = webSock.pollingTypeMap.get(type);
        return sendMessageToClient(set, type, deviceIds, webSock);
    }


    private boolean sendValuesMessage(String type, WebSocketServer webSock) {
        Collection<String> set = webSock.pollingTypeMap.get(type);
        return sendMessageToClient(set, type, webSock);
    }


    /**
     * @return 发送消息给socket
     * @throws
     * @Author laoxia
     * @Date 2021/8/19 17:32
     */
    private boolean sendMessageToClient(Collection<String> keySet, String type, WebSocketServer webSock) {
        if (keySet != null && keySet.size() != 0) {
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

    /**
     * @return 发送消息给指定socket
     * @throws
     * @Author laoxia
     * @Date 2021/8/19 17:32
     */
    private boolean sendMessageToClient(Collection<String> keySet, String type, Set deviceIds, WebSocketServer webSock) {
        if (keySet != null && keySet.size() != 0) {
            StringBuffer sendBuffer = appendAllValueFromRedis(keySet, deviceIds);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("infotype", type);
            jsonObject.addProperty("data", sendBuffer.toString());
            // 数据获取完，发送给客户端
            if (sendBuffer != null && sendBuffer.length() > 2)
                webSock.sendMessage(jsonObject.toString());
            return true;
        } else
            return false;
    }


    // 把Redis中的value组合成一个json的字符串
    private StringBuffer appendAllValueFromRedis(Collection<String> keySet) {
        int i = 0;// 当前计数器
        keySet.size();
        StringBuffer sendBuffer = new StringBuffer("[");

        for (String key : keySet) {
            String strFrame;
            try {
                strFrame = redisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                log.info("RedisConnectionException: Unable to connect to redis:6379,key:%s", key);
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

    private StringBuffer appendAllValueFromRedis(Collection<String> keySet, Set deviceIds) {
        int i = 0;// 当前计数器
        keySet.size();
        StringBuffer sendBuffer = new StringBuffer("[");
        for (String key : keySet) {
            // 并不是所有设备的消息都要拼接，这里需要过滤
            String[] params = key.split(":");
            String agentid = params[params.length - 1].trim();
            if (deviceIds == null || !deviceIds.contains(agentid)) continue;
            String strFrame = "";
            try {
                strFrame = redisTemplate.opsForValue().get(key.replace(" ", ""));
            } catch (Exception e) {
                log.info("RedisConnectionException: Unable to connect to redis:6379,key:%s", key);
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
