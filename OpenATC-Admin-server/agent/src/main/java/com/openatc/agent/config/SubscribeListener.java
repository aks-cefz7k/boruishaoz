package com.openatc.agent.config;

import com.openatc.agent.service.DevIdMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class SubscribeListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(SubscribeListener.class.toString());
    @Autowired
    DevIdMapService devIdMapService;

    /**
     * 收到到updateIdMap后，触发更新id映射
     * @param message：订阅消息类型
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {

        devIdMapService.initMap();
    }


}
