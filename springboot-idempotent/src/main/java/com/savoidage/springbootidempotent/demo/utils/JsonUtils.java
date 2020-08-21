package com.savoidage.springbootidempotent.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-21 16:59
 * Description: json 帮助类
 */
public class JsonUtils {
    private static ObjectMapper defaultMapper;
    private static Map<DateFormat,ObjectMapper> dateFormatMappers = new ConcurrentHashMap<DateFormat, ObjectMapper>();
    static{
        defaultMapper = new ObjectMapper();
        defaultMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        defaultMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        defaultMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * 将对象转为json字符串(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param obj 对象
     * @return 转换后的json字符串
     */
    public static String objectToJson(Object obj){
        return objectToJson(obj,null);
    }

    /**
     * JSON字符串转对象(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param json json字符串
     * @param clazz 要转换的对象的class
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        return jsonToObject(json,clazz,null);
    }

    /**
     * JSON字符串转对象(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param json json字符串
     * @param typeReference 要转换的对象的TypeReference
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToOjbect(String json, TypeReference<T> typeReference){
        return jsonToObject(json,typeReference,null);
    }

    /**
     * 字节流转对象(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param inputStream 要转换的字节流
     * @param clazz 要转换的对象的class
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToOjbect(InputStream inputStream, Class<T> clazz){
        return jsonToObject(inputStream,clazz,null);
    }

    /**
     * 字节流转对象(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param inputStream 要转换的字节流
     * @param typeReference 要转换的对象的TypeReference
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(InputStream inputStream, TypeReference<T> typeReference){
        return jsonToObject(inputStream,typeReference,null);
    }

    /**
     * JSON字符串转对象(默认日期格式:yyyy-MM-dd HH:mm:ss)
     * @param json json字符串
     * @param javaType 要转换的对象的JavaType
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, JavaType javaType){
        return jsonToObject(json,javaType,null);
    }

    /**
     * 将对象转为json字符串
     * @param obj 对象
     * @param dateFormat 转换的日期格式化
     * @return 转换后的json字符串
     */
    public static String objectToJson(Object obj, DateFormat dateFormat){
        String text=null;
        if(null!=obj){
            ObjectMapper objectMapper;
            if(null==dateFormat){
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                text = objectMapper.writeValueAsString(obj);
            }
            catch (JsonProcessingException e){
                throw new RuntimeException("Object转换Json格式出错:object="+obj,e);
            }
        }
        return text;
    }

    /**
     * JSON字符串转对象
     * @param json json字符串
     * @param clazz 要转换的对象的class
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz, DateFormat dateFormat){
        T t = null;
        if(null!=json){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(json,clazz);
            }catch (Exception e){
                throw new RuntimeException("Json转Class格式出错:Json="+json+",class="+clazz.getName(),e);
            }
        }
        return t;
    }

    /**
     * JSON字符串转对象
     * @param json json字符串
     * @param typeReference 要转换的对象的TypeReference
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, TypeReference<T> typeReference, DateFormat dateFormat){
        T t = null;
        if(null!=json){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(json,typeReference);
            }catch (Exception e){
                throw new RuntimeException("Json转Class格式出错:Json="+json+",typeReference="+typeReference.getType(),e);
            }
        }
        return t;
    }

    /**
     * JSON字符串转JavaType对象
     * @param json json字符串
     * @param javaType 要转换的对象的JavaType
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, JavaType javaType, DateFormat dateFormat){
        T t = null;
        if(null!=json){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(json,javaType);
            }catch (Exception e){
                throw new RuntimeException("Json转Class格式出错:Json="+json+",class="+javaType,e);
            }
        }
        return t;
    }

    /**
     * 字节流转对象
     * @param inputStream 序列化后的字节流
     * @param clazz 要转换的对象的class
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(InputStream inputStream, Class<T> clazz, DateFormat dateFormat){
        T t = null;
        if(null!=inputStream){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(inputStream,clazz);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    /**
     * 字节流转对象
     * @param inputStream 序列化后的字节流
     * @param typeReference 要转换的对象的TypeReference
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(InputStream inputStream, TypeReference<T> typeReference, DateFormat dateFormat){
        T t = null;
        if(null!=inputStream){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(inputStream,typeReference);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    /**
     * 只读流转对象
     * @param reader 序列化后的只读流
     * @param clazz 要转换的对象的class
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(Reader reader, Class<T> clazz, DateFormat dateFormat){
        T t = null;
        if(null!=reader){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(reader,clazz);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    /**
     * 只读流转对象
     * @param reader 序列化后的只读流
     * @param typeReference 要转换的对象TypeReference
     * @param dateFormat 日期格式类型
     * @param <T> 目标泛型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(Reader reader, TypeReference<T> typeReference, DateFormat dateFormat){
        T t = null;
        if(null!=reader){
            ObjectMapper objectMapper;
            if(null == dateFormat) {
                objectMapper = defaultMapper;
            }else{
                objectMapper = getFromMap(dateFormat);
            }
            try{
                t = objectMapper.readValue(reader,typeReference);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    /**
     * 获取泛型的JavaType
     * @param objectClass 泛型的ObjectClass
     * @param elementClasses 元素类
     * @return java类型
     */
    public static JavaType getJavaType(Class<?> objectClass, Class<?>... elementClasses){
        return defaultMapper.getTypeFactory().constructParametrizedType(objectClass,objectClass,elementClasses);
    }

    /**
     * 从json序列后的字节流中读取JsonNode
     * @param inputStream 字节流
     * @return
     */
    public static JsonNode readTree(InputStream inputStream){
        JsonNode node = NullNode.getInstance();
        try{
            node = defaultMapper.readTree(inputStream);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return node;
    }

    /**
     * object 转 对象
     * @param object 需要转换的object
     * @param clazz 对象类型
     * @param <T> 目标泛型
     * @return 对象
     */
    public static <T> T  objectToObject(Object object, Class<T> clazz){
        String jsonString = objectToJson(object);
        return jsonToObject(jsonString,clazz);
    }

    /**
     * 判断字符串是否是可换换对象的json格式
     * @param json 字符串
     * @return
     */
    public static boolean isJson(String json){
        try{
            jsonToObject(json,Object.class);
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }

    private static ObjectMapper getFromMap(DateFormat key){
        ObjectMapper objectMapper = dateFormatMappers.get(key);
        if(null==objectMapper){
            objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(key);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            dateFormatMappers.put(key,objectMapper);
        }
        return objectMapper;
    }
}
