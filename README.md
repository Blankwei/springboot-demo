# 新版本springboot demo篇

## springboot-idempotent模块做幂等性demo
    springboot 版本 2.3.3  
    mybatis 2.0.1  
    连接池：hikari
 
 ### 更新了接口幂等操作主要实现了三种方式：
 1.注解+redis+拦截器  
    
 2.注解+redis+aop  
 
 3.数据库唯一约束
 
 
## springboot-distributedlock模块做分布式锁demo
    springboot 版本 2.1.16

实现方式redis+注解+aop的方式 只需在需要锁的方法上增加注解即可 便于维护和拓展

## springboot-distributedtransaction模块做分布式事务demo
    springboot 版本 2.3.3
    mybatis 2.0.1  
    连接池：hikari