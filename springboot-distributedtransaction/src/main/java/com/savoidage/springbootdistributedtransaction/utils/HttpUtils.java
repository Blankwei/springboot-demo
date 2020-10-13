package com.savoidage.springbootdistributedtransaction.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtils {
    /**
     * 发送get请求
     * @param url 请求url和参数
     * @return 请求结果
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        return get(url, null);
    }
    /**
     * 发送get请求
     * @param url 请求url
     * @param params 请求参数
     * @return 请求结果
     * @throws IOException
     */
    public static String get(String url, List<NameValuePair> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String body = null;

        if (params != null && !params.isEmpty()) {
            String paraStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            url += "?" + paraStr;
        }

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity,"utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return body;
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param params 请求参数
     * @return 请求结果
     * @throws Exception
     */
    public static String post(String url, List<NameValuePair> params) throws Exception {
        String body = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        if (isHttpsRequest(url)) {
            httpPost.setHeader("referer", getHttpsUrl(url));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity,"utf-8");
            EntityUtils.consume(entity);
        } finally {
            httpPost.abort();
            response.close();
        }
        return body;
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param params 请求参数
     * @return 请求结果
     * @throws Exception
     */
    public static String post(String url, Map<String,Object> params) throws Exception {
        List<NameValuePair> list = convertMapToNameValuePair(params);
        return post(url,list);
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param params 请求参数
     * @param isReplaceCode 是否替换 + 为 %20
     * @return 请求结果
     * @throws Exception
     */
    public static String post(String url, Map<String,Object> params,boolean isReplaceCode) throws Exception {
        String args = "";
        for(String key:params.keySet()){
            if(!"".equals(args)){
                args+="&";
            }
            args += key;
            args += "=";
            String value = params.get(key).toString();
            if(isReplaceCode){
                value = value.replaceAll("\\+","%20");
            }
            args+=value;
        }
        Map<String,String> headers = new HashMap<String, String>();
        return post(url,args,headers);
    }

    /**
     * 发送post请求(可设置Headers)
     * @param url 请求url
     * @param params 请求参数
     * @param headers 请求头
     * @return 请求结果
     * @throws Exception
     */
    public static String post(String url, String params,Map<String,String> headers) throws Exception {
        String body = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity data = new StringEntity(params, Charset.forName("UTF-8"));
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        HttpPost httpPost = new HttpPost(url);
        for(Map.Entry<String,String> map:headers.entrySet())
            httpPost.setHeader(map.getKey(), map.getValue());
        httpPost.setEntity(data);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity,"utf-8");
            EntityUtils.consume(entity);
        } finally {
            httpPost.abort();
            response.close();
        }
        return body;
    }

    /**
     * 发送post请求(可设置文件)
     * @param url 请求url
     * @param params 请求参数
     * @param fileParas 请求文件
     * @return 请求结果
     * @throws Exception
     */
    public static String post(String url, List<NameValuePair> params, Map<String, File> fileParas) throws Exception {
        String body = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (NameValuePair ele : params) {
            builder.addTextBody(ele.getName(), ele.getValue());
        }
        if (fileParas != null && !fileParas.isEmpty()) {
            for (String key : fileParas.keySet()) {
                if (key != null) {
                    File file = fileParas.get(key);
                    if (file != null) {
                        builder.addPart(key, new FileBody(file));
                    }
                }
            }
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(builder.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity,"utf-8");
            EntityUtils.consume(entity);
        } finally {
            httpPost.abort();
            response.close();
        }
        return body;

    }

    /**
     * 发送post的json请求
     * @param url 请求url
     * @param json json字符串数据
     * @return json 字符串数据
     * @throws Exception
     */
    public static String postJson(String url,String json) throws Exception{
        String body = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json);
        stringEntity.setContentEncoding("utf-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity,"utf-8");
            EntityUtils.consume(entity);
        } finally {
            httpPost.abort();
            response.close();
        }
        return body;
    }

    /**
     * 检查 url是否是https请求
     * @param url 请求url
     * @return
     */
    private static boolean isHttpsRequest(String url) {
        return url.trim().startsWith("https://");
    }

    /**
     * 获取https请求url部分字符串
     * @param url https请求url
     * @return url部分
     */
    private static String getHttpsUrl(String url) {
        int start = "https://".length();
        int end = url.substring(start).indexOf('/');
        String refer = url.substring(0, start + end);
        return refer;
    }

    /**
     * 获取请求头集合信息
     * @param request 请求对象
     * @return map集合
     */
    public static Map<String,String> getHeaders(HttpServletRequest request){
        Map<String,String> map =  new LinkedHashMap<String,String>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            System.out.println("HEADER - "+key+":"+value);
            map.put(key,value);
        }
        return map;
    }

    /**
     * 获取指定的请求头信息
     * @param request 请求对象
     * @param key 指定头键值
     * @return
     */
    public static String getHeader(HttpServletRequest request, String key){
        Map<String,String> headers = getHeaders(request);
        for(Map.Entry<String,String> map:headers.entrySet()){
            if(map.getKey().equals(key)){
                return map.getValue();
            }
        }
        return null;
    }

    /**
     * 将map对象转换成NameValuePair列表
     * @param params 需要转换的对象
     * @return NameValuePair列表
     */
    public static List<NameValuePair> convertMapToNameValuePair(Map<String,Object> params){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if(null!=params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                NameValuePair pair = new BasicNameValuePair(key,value);
                list.add(pair);
            }
        }
        return list;
    }
}
