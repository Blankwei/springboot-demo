package com.savoidage.springbootdistributedlock.annotation;

import java.lang.annotation.*;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-25 14:56
 * Description: redis实现分布式锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {

    /**
     * 锁资源名称 redis key
     * @return
     */
    String value() default "default";

    /**
     * 持锁时间 单位秒 默认30s
     * @return
     */
    long keepMills() default 30L;
}
