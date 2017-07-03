package com.lanwei.haq.comm.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网络请求工具类
 *
 * @author sunxushe
 * @created 2016/12/22 11:46
 */
public class HttpUtil {

    private final static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 默认字符类型
     */
    private final static String CHARSET = "UTF-8";

    /**
     * get请求
     * @param url
     */
    public static int get(String url){
        int code = 0;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            code = response.getStatusLine().getStatusCode();
        }catch (IOException e){
            e.printStackTrace();
        }
        return code;
    }

    /**
     * post请求，参数为字符串
     *
     * @param url  请求地址
     * @param body 请求体
     * @return
     */
    public static String post(String url, String body) {
        return post(url, body, CHARSET, CHARSET);
    }

    /**
     * 表单类型请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String postForm(String url, Map<String, String> params) {
        return postForm(url, params, CHARSET);
    }

    /**
     * 表单类型请求,自定义参数字符码
     *
     * @param url
     * @param params
     * @return
     */
    public static String postForm(String url, Map<String, String> params, String charset) {
        HttpPost post = new HttpPost(url);
        if (null != params && !params.isEmpty()) {
            // 参数不为空拼接表单
            List<NameValuePair> list = new ArrayList<>();
            for (String key : params.keySet()) {
                list.add(new BasicNameValuePair(key, params.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(list, Charset.forName(charset)));
        }
        return execute(post, charset);
    }

    /**
     * post请求，参数为字符串
     *
     * @param url             请求地址
     * @param body            请求体
     * @param responseCharset 响应的结果字符类型
     * @return
     */
    public static String post(String url, String body, String responseCharset) {
        return post(url, body, CHARSET, responseCharset);
    }

    /**
     * post请求，参数为字符串
     *
     * @param url             请求地址
     * @param body            请求体
     * @param paramCharset    请求的参数字符类型
     * @param responseCharset 响应的结果字符类型
     * @return
     */
    public static String post(String url, String body, String paramCharset, String responseCharset) {
        HttpPost post;
        String result;
        long start = System.currentTimeMillis();
        log.info("[HttpUtil]开始发起POST请求，请求地址为：{} ，请求参数为：{}", url, body);

        post = new HttpPost(url);
        if (StringUtils.isNotBlank(body)) {
            post.setEntity(new StringEntity(body, paramCharset));
        }
        result = execute(post, responseCharset);

        log.info("[HttpUtil]POST请求完成，请求地址为：{} ，请求参数为：{}", url, body);
        log.info("[HttpUtil]POST请求完成，耗时{}ms", (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * 执行请求post请求
     *
     * @param post
     * @param charset
     * @return
     */
    protected static String execute(HttpPost post, String charset) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        String url = post.getURI().toString();
        try {
            httpClient = getHttpClient();
            response = httpClient.execute(post);
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (UnsupportedEncodingException e) {
            log.error("[HttpUtil]发起post类型的请求异常，不支持的字符类型{}，请求地址为：{}", charset, url, e);
        } catch (ClientProtocolException e) {
            log.error("[HttpUtil]发起post类型的请求异常，客户端协议异常，请求地址为：{}", url, e);
        } catch (IOException e) {
            log.error("[HttpUtil]发起post类型的请求异常，请求地址为：{}", url, e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("[HttpUtil]关闭post类型的请求响应异常，请求地址为：{} ", url, e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("[HttpUtil]关闭post类型的请求异常，请求地址为：{} ", url, e);
                }
            }
        }
        return null;
    }

    /**
     * 获取CloseableHttpClient
     *
     * @return
     */
    protected static CloseableHttpClient getHttpClient() {
        HttpClientBuilder httpBuilder = HttpClientBuilder.create();
        return httpBuilder.build();
    }

}
