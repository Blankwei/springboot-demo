package com.savoidage.springbootdistributedlock.aspect;

import com.savoidage.springbootdistributedlock.annotation.RedisLock;
import com.savoidage.springbootdistributedlock.utils.RedisLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-25 15:04
 * Description: redis锁切面
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class RedisLockAspect {

    @Resource
    private RedisLockHelper redisLockHelper;

    /**
     * 超时时间 10s
     */
    private static final int time_out_mills = 10 * 1000;

    private static final String prefix = "redis_lock_prefix_";

    @Pointcut("@annotation(com.savoidage.springbootdistributedlock.annotation.RedisLock)")
    private void lockPoint() {
    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        // 获取锁的key
        String redisLockKey = annotation.value();
        if (!StringUtils.isEmpty(redisLockKey)) {
            Object[] args = joinPoint.getArgs();
            redisLockKey = redisLockKey.concat(Arrays.toString(args));
        }
        // 获取锁的value
        long redisLockVlaue = System.currentTimeMillis() + time_out_mills;
        Boolean aBoolean = redisLockHelper.lock(prefix + redisLockKey, String.valueOf(redisLockVlaue));
        if (!aBoolean) {
            log.debug("获取锁失败：" + redisLockKey);
            // 抛异常 或者提示 排队中等
            return null;
        }
        log.info("获取锁成功：" + redisLockKey);
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 释放锁
            boolean releaseLock = redisLockHelper.releaseLock(redisLockKey, String.valueOf(redisLockVlaue));
            if (!releaseLock) {
                log.debug("释放锁失败：" + redisLockKey);
            }
        }
        return null;
    }
}
