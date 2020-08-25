package com.savoidage.springbootidempotent.aspect;

import com.savoidage.springbootidempotent.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 15:48
 * Description: 幂等切面
 */
@Aspect
@Order(1)
@Slf4j
@Component
public class IdempotentAspect {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 幂等注解
     */
    @Pointcut("@annotation(com.savoidage.springbootidempotent.annotation.ApiIdempotent)")
    public void executeIdempotent() {
    }

    @Around("executeIdempotent()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        /*// 获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取幂等注解
        ApiIdempotent idempotent = method.getAnnotation(ApiIdempotent.class);
        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            // 缓存是否存在
            // 如果不存在 重新设置key 默认过期时间为注解默认值
            // 如果存在 就返回操作提示
            if (!redisUtils.exists(token)) {
                boolean set = redisUtils.set(token, token, idempotent.expiredTime());
                if (!set) {
                    throw new ServiceException(SysConstant.REPETITIVE_OPERATION);
                }
                return joinPoint.proceed();
            } else {
                // 重复操作提示
                throw new ServiceException(SysConstant.REPETITIVE_OPERATION);
            }
        }*/
        // 默认返回方法的实例
        return joinPoint.proceed();
    }
}
