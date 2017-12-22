package com.lanwei.haq.comm.util;

/**
 * @作者：刘新运
 * @日期：2017/7/9 10:39
 * @描述：类
 */

public class Constant {

    //爬虫伪装浏览器使用
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36";

    //年月日时分秒
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    //年月日
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    //年月日时
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH:00:00";

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
    /**
     * 默认新闻类别其他
     */
    public static final String NEWS_CLASS_OTHER = "5";

}
