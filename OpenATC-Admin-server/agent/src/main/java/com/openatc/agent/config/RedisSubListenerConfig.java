package com.openatc.agent.config;

import com.openatc.agent.gateway.ChannelReceiver;
import com.openatc.agent.gateway.FaultIncidentReceiver;
import com.openatc.agent.gateway.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.util.Assert;

/**
 * @author laoxia
 * @Date 2021/7/28 13:59
 * @Describetion
 */
@Configuration
public class RedisSubListenerConfig {
    private Logger log = LoggerFactory.getLogger(RedisSubListenerConfig.class);

    @Autowired
    private FaultIncidentReceiver faultIncidentReceiver;
    @Autowired
    private ChannelReceiver channelReceiver;

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param redisConnectionFactory
     * @return
     */

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        //订阅频道
        container.addMessageListener(channelReceiver, new PatternTopic("asc:status/channellamp"));
        container.addMessageListener(faultIncidentReceiver, new PatternTopic("asc:event/faultdata"));
        container.setErrorHandler(e -> {
            log.info("message: " + e.getMessage());
            log.info("cause: " + e.getCause());
        });
        return container;
    }

    /**
     * 绑定消息处理器，利用反射技术调用消息处理器的业务方法
     *
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisService receiver) {
        return new MessageListenerAdapter(receiver, "receiveSubsMessage");
    }


}
