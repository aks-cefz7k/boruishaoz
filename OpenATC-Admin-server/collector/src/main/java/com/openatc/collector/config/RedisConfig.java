package com.openatc.collector.config;

import com.openatc.collector.service.ChannelListener;
import com.openatc.collector.service.PatternListener;
import com.openatc.collector.service.VolumeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author zhangwenchao
 * @Date Created in 2021/10/9 13:28
 * @Describetion
 */
@Configuration
public class RedisConfig{
    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Autowired
    PatternListener patternListener;
    @Autowired
    VolumeListener volumeListener;
    @Autowired
    ChannelListener channelListener;
    /**
     * 配置 发布/订阅 的 Topic
     * */
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("asc:status/pattern");
    }

    /**
     * 将消息监听器绑定到消息容器
     * */
    @Bean
    public RedisMessageListenerContainer messageListenerContainer() {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

//        MessageListener ml = new PatternListener();
        // 固定通道匹配
//        container.addMessageListener(ml, new ChannelTopic("asc:status/pattern"));

        // 模式匹配
        container.addMessageListener(patternListener, new PatternTopic("asc:status/pattern:*"));
        container.addMessageListener(volumeListener, new PatternTopic("asc:status/currentvolume:*"));
        container.addMessageListener(channelListener, new PatternTopic("asc:status/channel:*"));

        return container;
    }


}
