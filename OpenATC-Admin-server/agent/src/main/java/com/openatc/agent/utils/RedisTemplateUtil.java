package com.openatc.agent.utils;

import com.google.gson.Gson;
import com.openatc.comm.data.MessageData;
import com.openatc.model.model.StatusPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author laoxia
 * @Date 2021/7/28 14:09
 * @Describetion
 */
@Component
public class RedisTemplateUtil {

    private String agenttype = "asc";
    private Gson gson = new Gson();

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 向redis中添加一个集合
     *
     * @param key
     * @param set
     */
    public void addSet(String key, Set<String> set) {
        for (String value : set) {
            redisTemplate.opsForSet().add(key, value);
        }
    }

    /**
     * 删除redis中的一个集合元素
     *
     * @param key
     * @param set
     */
    public void removeSet(String key, Set<String> set) {
        for (String value : set) {
            if (redisTemplate.opsForSet().isMember(key, value)) {
                redisTemplate.opsForSet().remove(key, value);
            }
        }
    }

    /**
     * 获取key的所有值
     *
     * @param key
     * @return
     */
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取匹配的key
     *
     * @param pattern
     * @return
     */
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public void setValue(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void publish(String channel, String message){
        redisTemplate.convertAndSend(channel, message);
    }
    /**
     * @Author: yangyi
     * @Date: 2022/1/5 9:42
     * @Description: get current pattern from redis
     */
    public StatusPattern getStatusPatternFromRedis (String agentId) {
        StatusPattern statusPattern = new StatusPattern();
        String key = agenttype + ":" + "status/pattern" + ":" + agentId;
        String s = redisTemplate.opsForValue().get(key);
        MessageData messageData = gson.fromJson(s, MessageData.class);
        statusPattern = gson.fromJson(messageData.getData().getAsJsonObject(),StatusPattern.class);
        return statusPattern;
    }
}
