package com.openatc.agent.gateway;

import com.alibaba.fastjson.JSONObject;
import com.openatc.agent.controller.MessageSubController;
import com.openatc.agent.utils.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author laoxia
 * @Date 2021/7/28 14:04
 * @Describetion
 */
@Component
@Slf4j
public class ChannelReceiver implements MessageListener, InitializingBean {


    @Autowired
    private MessageSubController messageSubController;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private RestTemplate restTemplate;

    private static int poolSize = Runtime.getRuntime().availableProcessors();


    @Override
    public void afterPropertiesSet() throws Exception {
        if (messageSubController.receiveAddrMap == null || messageSubController.receiveAddrMap.size() == 0) {
            List<String> keys = redisTemplateUtil.keys("status/channellamp*");
            for (String key : keys) {
                messageSubController.receiveAddrMap.put(key, redisTemplateUtil.get(key));
            }
        }
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6));
        JSONObject revObj = JSONObject.parseObject(message.toString());
        String agentId = revObj.getString("agentid");
        messageSubController.receiveAddrMap.forEach((receiveAddr, set) -> {
            if (set.contains(agentId)) {
                executor.execute(() -> {
                    try {
                        log.info(receiveAddr.replace("status/channellamp:", ""));
                        restTemplate.postForObject(receiveAddr.replace("status/channellamp:", ""), revObj, JSONObject.class);
                    } catch (Exception e) {
                        log.info("exception happen: " + e.getMessage());
                    }
                });
            }
        });
        executor.shutdown();
    }
}
