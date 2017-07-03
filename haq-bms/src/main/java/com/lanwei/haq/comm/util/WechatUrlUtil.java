package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @作者: liuxinyun
 * @日期: 2017/3/29 14:14
 * @描述: 微信短链接
 */
public class WechatUrlUtil {

    private static final Logger log = LoggerFactory.getLogger(WechatUrlUtil.class);

    /**
     * AppId
     */
    private static final String APP_ID = "wx0face8e12c342142";
    /**
     * AppSecret
     */
    private static final String APP_SECRET = "8932c96b13e323104a7df5a06773c42c";
    /**
     * 获取token地址
     */
    private static final String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 生成短链接地址
     */
    private static final String WECHAT_CREATE = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
    /**
     * token
     */
    private static String TOKEN = null;


    /**
     * 生成短链接
     * @param url
     * @return
     */
    public static String  createTinyUrl(String url) {

        if (StringUtils.isBlank(url)) {
            log.error("创建短连接失败，原因为：网址不能为空！");
            return null;
        }

        if (StringUtils.isBlank(TOKEN)){
            Timer t = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    TOKEN = null;
                }
            };
            t.schedule(task,100*60*1000);//100分钟后执行
            TOKEN = getAccessToken(APP_ID,APP_SECRET);
        }

        // 参数
        Map<String, Object> params = new HashMap<>();
        params.put("action","long2short");
        params.put("long_url",url);
        String jsonStr = HttpConnectionUtil.postStringSSL(WECHAT_CREATE.replace("ACCESS_TOKEN",TOKEN), params);
        System.out.println("创建短链接请求返回参数为："+jsonStr);
        log.info("创建短链接请求返回参数为：[{}]",jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String shortUrl = object.getString("short_url");
        return shortUrl;
    }

    public static String getAccessToken(String appId, String appSecret){
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret)) {
            log.error("获取token失败，原因为：appid和secret均不能为空！");
            return null;
        }
        String jsonStr = HttpConnectionUtil.getStringSSL(GET_TOKEN.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET));
        System.out.println("获取token请求返回参数为："+jsonStr);
        log.info("获取token请求返回参数为：[{}]",jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String token = object.getString("access_token");
        return token;
    }


    public static void main(String[] args) {
        String tinyUrl = createTinyUrl("http://101.201.73.102:38080/mobile/index.html?param=88&branch=1");
        System.out.println(tinyUrl);
        String tinyUrl2 = createTinyUrl("http://101.201.73.102:38080/mobile/index.html?param=94&branch=1");
        System.out.println(tinyUrl2);
    }

}
