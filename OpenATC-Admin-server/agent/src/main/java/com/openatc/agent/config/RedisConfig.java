package com.openatc.agent.config;

import com.openatc.agent.model.Fault;
import com.openatc.agent.service.impl.FaultReceiver;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@EnableCaching
public class RedisConfig {
    /**
     * Redis消息监听器容器
     * @param connectionFactory
     * @return
     */
    @Autowired
    private ObjectFactory<MessageListener> objectFactory;

    @Bean
    @Conditional(RedisSubEnable.class)
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫"asc:status/fault"的通道
        MessageListener faultReceiver = objectFactory.getObject();
        container.addMessageListener(faultReceiver, new PatternTopic("asc:status/fault"));
        return container;
    }
}
