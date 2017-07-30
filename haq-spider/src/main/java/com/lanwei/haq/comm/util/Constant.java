package com.lanwei.haq.comm.util;

/**
 * @作者：刘新运
 * @日期：2017/7/9 10:39
 * @描述：类
 */

public class Constant {

    //年月日时分秒
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    //年月日
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    //爬虫存储库
    public static final Integer REDIS_SAVE_INDEX = 0;
    //爬虫统计库
    public static final Integer REDIS_STATISTIC_INDEX = 1;
    //爬虫使用redis轮询交互库
    public static final Integer REDIS_TCP_INDEX = 2;
    //网站站点redis所用前缀，后边存储网站域名和id，例如website_sina_2
    public static final String REDIS_WEBSITE_PREFIX = "website_";
    //网站配置redis键
    public static final String REDIS_WEBCONFIG_KEY = "webconfig";


}
