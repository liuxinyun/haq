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
    public static final String YYYYMMDD = "yyyyMMdd";
    //年月日时
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH:00:00";

    //爬虫存储库
    public static final Integer REDIS_SAVE_INDEX = 0;
    //爬虫统计库
    public static final Integer REDIS_STATIS_INDEX = 1;
    //爬虫使用redis轮询交互库
    public static final Integer REDIS_TCP_INDEX = 2;
    //网站站点redis所用前缀，后边存储网站域名和id，例如website_sina_2
    public static final String REDIS_WEBSITE_PREFIX = "website_";
    //网站配置redis键
    public static final String REDIS_WEBCONFIG_KEY = "webconfig";
    //统计各网站接入量web_news.sina.com.cn
    public static final String REDIS_WEB_PREFIX = "web_";
    //统计网站接入总量total_webcount
    public static final String REDIS_TOTAL_PREFIX = "total_webcount";
    //统计各分类class_classId
    public static final String REDIS_CLASS_PREFIX = "class_";
    //统计各专题subject_subjectId
    public static final String REDIS_SUBJECT_PREFIX = "subject_";
    //统计各地域area_areaId
    public static final String REDIS_AREA_PREFIX = "area_";

    //新闻类别
    public static final class NewsClass{
        //政治
        public static final int GOV  = 2;
        //军事
        public static final int WAR = 3;
        //科技
        public static final int TECH = 4;
        //其他
        public static final int OTHER = 5;
    }

}
