package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @作者: liuxinyun
 * @日期: 2017/3/29 11:06
 * @描述:
 */
public class DwzShortUrlUtil {

    private static final Logger log = LoggerFactory.getLogger(DwzShortUrlUtil.class);

    /**
     * 百度生成短链接地址
     */
    private static final String DMZ_CREATE = "http://dwz.cn/create.php";

    /**
     * 百度查询原连接地址
     */
    private static final String DMZ_QUERY = "http://dwz.cn/query.php";

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

        // 参数
        Map<String, String> params = new HashMap<>();
        params.put("url", url);
        String jsonStr = HttpUtil.postForm(DMZ_CREATE, params, "utf-8");
        log.info("创建短链接请求返回参数为：[{}]",jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String tinyurl = object.getString("tinyurl");
        return tinyurl;
    }

    /**
     * 根据短链接查询原地址
     * @param tinyurl
     * @return
     */
    public static String  queryUrl(String tinyurl) {

        if (StringUtils.isBlank(tinyurl)) {
            log.error("查询失败，原因为：网址不能为空！");
            return null;
        }

        // 参数
        Map<String, String> params = new HashMap<>();
        params.put("tinyurl", tinyurl);
        String jsonStr = HttpUtil.postForm(DMZ_QUERY, params, "utf-8");
        log.info("根据短链接查询请求返回参数为：[{}]",jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        String longurl = object.getString("longurl");
        return longurl;
    }


    public static void main(String[] args) {
        String tinyUrl = createTinyUrl("http://help.baidu.com/index");
        System.out.println(tinyUrl);
        String longurl = queryUrl(tinyUrl);
        System.out.println(longurl);
    }

}
