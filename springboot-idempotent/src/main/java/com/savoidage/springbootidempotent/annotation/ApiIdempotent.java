package com.savoidage.springbootidempotent.annotation;

import java.lang.annotation.*;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 10:36
 * Description: 幂等注解
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiIdempotent {

    /**
     * 幂等名称
     * @return
     */
    String nameValue();

    /**
     * 幂等过期时间 默认60s
     * @return
     */
    long expiredTime() default 60L;
}
