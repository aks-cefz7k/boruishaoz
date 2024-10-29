package com.openatc.agent.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author laoxia
 * @Date 2021/7/28 14:34
 * @Describetion
 */
@Component
@Slf4j
public class FaultIncidentReceiver extends IncidentReceiver implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        dealIncidentMessage(message, WebSocketServer.getFaultIncidentWebSocketSet());
    }
}
