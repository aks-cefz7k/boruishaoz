package com.openatc.collector.service;

import com.google.gson.Gson;
import com.openatc.comm.data.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/10/9 13:28
 * @description：接口实现类
 * @modified By：
 * @version: $
 */
@Component
public class PatternListener implements MessageListener {

    private static Logger logger = Logger.getLogger(PatternListener.class.toString());


    @Value("${spring.influx.enable}")
    private boolean isInfluxDBEnable;

    @Autowired
    private InfluxDbUtils influxDbUtils;

    Gson gson = new Gson();

    /**
     * <h2>消息回调</h2>
     *
     * @param message {@link Message} 消息体 + ChannelName
     * @param pattern 订阅的 pattern, ChannelName 的模式匹配
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String body = new String(message.getBody());
        String channel = new String(message.getChannel());
        String pattern_ = new String(pattern);

        logger.info(Thread.currentThread().getId() + ":" + Thread.currentThread().getName() + " @ " + channel);
//        logger.info(pattern_ + " - " +  channel + " - " + body);

        MessageData msg = gson.fromJson(body, MessageData.class);

        // 将方案信息保存到InfluxDB中
        if (isInfluxDBEnable){
            influxDbUtils.insertPattern(msg);
        }
    }
}
