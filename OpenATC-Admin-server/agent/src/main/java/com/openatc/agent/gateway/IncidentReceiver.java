package com.openatc.agent.gateway;

import com.openatc.agent.model.MyWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * @author laoxia
 * @Date 2021/7/28 14:35
 * @Describetion
 */
@Component
@Slf4j
public class IncidentReceiver implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] bytes) {

    }

    protected void dealIncidentMessage(Message message, Map<Session, MyWebSocketServer> incidentWebSocketSet) {
        log.info("receive incident data: " + message.toString());
        for (Session session : incidentWebSocketSet.keySet()) {
            incidentWebSocketSet.get(session).getWebSocketServer().sendMessage(message.toString());
        }
    }
}