package com.savoidage.springbootdistributedlock.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-25 14:10
 * Description: redis lock help
 */
@Component
public class RedisLockHelper {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Redis加锁的操作
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean lock(String key, String value) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.valueOf(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间 如果高并发的情况可能会出现已经被修改的问题  所以多一次判断保证线程的安全
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            // 效验时间戳 防止并发
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Redis解锁的操作
     *
     * @param key
     * @param value
     */
    public boolean releaseLock(String key, String value) {
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        try {
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                // 删除锁
                return stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
