package com.openatc.agent.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


/**
 * redis订阅配置
 */
@Configuration
public class RedisConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * redis消息监听器
     * @return
     */
    @Bean
    public SubscribeListener listener(){
        return new SubscribeListener();
    }

    /**
     * 配置发布/订阅的Topic
     */
    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("updateIdMap");
    }

    /**
     * 消息监听器绑定到消息容器
     */
    @Bean
    public RedisMessageListenerContainer messageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(listener(),channelTopic());
        return container;
    }
}
