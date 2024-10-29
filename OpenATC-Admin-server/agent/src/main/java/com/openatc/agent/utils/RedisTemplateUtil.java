package com.openatc.agent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author laoxia
 * @Date 2021/7/28 14:09
 * @Describetion
 */
@Component
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate redisTemplate;

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
    public Set<String> get(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * scan 实现
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    public void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取符合条件的key
     *
     * @param pattern 表达式
     * @return
     */
    public List<String> keys(String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }
}
