package com.lanwei.haq.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者：刘新运
 * @日期：2017/7/2 20:59
 * @描述：网络工具类
 */

public class WebUtil {

    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    private static final String REGEX = "[0-9a-zA-Z]+((\\.com)|(\\.cn)|(\\.org)|(\\.net)|(\\.edu)|(\\.com.cn)|(\\.xyz)|(\\.xin)|(\\.club)|(\\.shop)|(\\.site)|(\\.wang)" +
            "|(\\.top)|(\\.win)|(\\.online)|(\\.tech)|(\\.store)|(\\.bid)|(\\.cc)|(\\.ren)|(\\.lol)|(\\.pro)|(\\.red)|(\\.kim)|(\\.space)|(\\.link)|(\\.click)|(\\.news)|(\\.news)|(\\.ltd)|(\\.website)" +
            "|(\\.biz)|(\\.help)|(\\.mom)|(\\.work)|(\\.date)|(\\.loan)|(\\.mobi)|(\\.live)|(\\.studio)|(\\.info)|(\\.pics)|(\\.photo)|(\\.trade)|(\\.vc)|(\\.party)|(\\.game)|(\\.rocks)|(\\.band)" +
            "|(\\.gift)|(\\.wiki)|(\\.design)|(\\.software)|(\\.social)|(\\.lawyer)|(\\.engineer)|(\\.org)|(\\.net.cn)|(\\.org.cn)|(\\.gov.cn)|(\\.name)|(\\.tv)|(\\.me)|(\\.asia)|(\\.co)|(\\.press)|(\\.video)|(\\.market)" +
            "|(\\.games)|(\\.science)|(\\.中国)|(\\.公司)|(\\.网络)|(\\.pub)" +
            "|(\\.la)|(\\.auction)|(\\.email)|(\\.sex)|(\\.sexy)|(\\.one)|(\\.host)|(\\.rent)|(\\.fans)|(\\.cn.com)|(\\.life)|(\\.cool)|(\\.run)" +
            "|(\\.gold)|(\\.rip)|(\\.ceo)|(\\.sale)|(\\.hk)|(\\.io)|(\\.gg)|(\\.tm)|(\\.com.hk)|(\\.gs)|(\\.us))";

    private static final String UNKNOWN = "unknown";

    /**
     * 根据网址获取一级域名
     * @param url
     * @return
     */
    public static String getDomain(String url){
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(url);
        String domain = null;
        //获取一级域名
        while(m.find()){
            domain = m.group();
        }
        return domain;
    }

    /**
     * 获取网站host
     * @param url
     * @return
     */
    public static String getHost(String url){
        String host = UNKNOWN;
        try {
            URL temp = new URL(url);
            host = temp.getHost();
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        }
        return host;
    }

    public static void main(String[] args) {
        System.out.println(getDomain("http://news.sina.com.cn/"));
        System.out.println(getHost("http://news.sina.com.cn/"));
    }

}
