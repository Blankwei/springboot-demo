package com.savoidage.springbootidempotent.demo.utils;

import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-21 16:59
 * Description: redis 工具类
 */
public class RedisUtils {

    private RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate ){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 写入缓存
     * @param key 存储键
     * @param value 对应值
     * @return boolean 是否存储成功的标识
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value) {
        boolean result = false;
        if(null == key || null == value) return false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key 存储键
     * @param value 对应值
     * @return boolean 是否存储成功的标识
     */
    @SuppressWarnings("unchecked")
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        if(null == key || null == value) return false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            expireTime = null == expireTime ? redisTemplate.getExpire(key, TimeUnit.SECONDS) : expireTime;
            if(expireTime < 0){
                operations.set(key,value);
            }else if(expireTime == 0L){
                redisTemplate.delete(key);
            }else{
                operations.set(key,value,expireTime,TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的键值
     * @param keys 参数键的对象(可以传递一个或者多个键)
     */
    public void remove(String... keys) {
        if(null != keys && keys.length>0){
            for (String key : keys) {
                remove(key);
            }
        }
    }

    /**
     * 删除对应的value
     * @param key 指定键
     */
    @SuppressWarnings("unchecked")
    public void remove(String key) {
        if(null == key){
            return;
        }
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key 指定键
     * @return 是否存在的标识
     */
    @SuppressWarnings("unchecked")
    public boolean exists(String key) {
        if(null == key){
            return false;
        }
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取缓存
     * @param key 读取指定键
     * @return 读取的数据值
     */
    @SuppressWarnings("unchecked")
    public Object get(String key) {
        if(null==key){
            return null;
        }
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }
    /**
     * 哈希 添加
     * @param key 存储指定键
     * @param hashKey 存储的哈希键
     * @param value 存储对应的哈希值
     */
    @SuppressWarnings("unchecked")
    public boolean hmSet(String key, Object hashKey, Object value){
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 哈希获取数据
     * @param key 指定键
     * @param hashKey 指定哈希键
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object hmGet(String key, Object hashKey){
        if(null == key || null == hashKey){
            return null;
        }
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @SuppressWarnings("unchecked")
    public boolean hdel(String key, Object... item){
        Long deleteCount = redisTemplate.opsForHash().delete(key, item);
        return deleteCount>0;
    }

    /**
     * 列表添加
     * @param key 添加指定键
     * @param value 添加指定值
     */
    @SuppressWarnings("unchecked")
    public void lPush(String key, Object value){
        if(null == key || null == value){
            return;
        }
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key,value);
    }

    /**
     * 列表获取
     * @param k 获取指定键
     * @param l 值1
     * @param l1 值2
     * @return 返回获取的列表结果
     */
    @SuppressWarnings("unchecked")
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key 指定键
     * @param value 对应的值
     */
    @SuppressWarnings("unchecked")
    public void setArray(String key, Object value){
        if(null == key || null == value){
            return;
        }
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key 获取集合的指定键
     * @return 返回查询的集合
     */
    @SuppressWarnings("unchecked")
    public Set<Object> getArray(String key){
        if(null == key){
            return null;
        }
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 设置已存入的键的过期时间
     * @param key 指定键
     * @param expire 过期时间
     */
    @SuppressWarnings("unchecked")
    public void setExpire(final String key, long expire){
        if(null == key){
            return;
        }
        redisTemplate.expire(key,expire,TimeUnit.SECONDS);
    }

    /**
     * 获取指定键的过期时间
     * @param key 指定键
     * @return 过期时间
     */
    @SuppressWarnings("unchecked")
    public Long getExpire(final String key){
        if(null == key){
            return null;
        }
        return redisTemplate.getExpire(key);
    }

    /**
     * 向redis中存入有序集合数据
     * @param key 键
     * @param tupleSet 有序集合数据
     */
    @SuppressWarnings("unchecked")
    public void setAdd(final String key, final Set<ZSetOperations.TypedTuple<String>> tupleSet){
        if(null == key){
            return;
        }
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,tupleSet);
    }

}
