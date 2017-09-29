package com.lanwei.haq.comm.util;

/**
 * @作者：刘新运
 * @日期：2017/7/9 10:39
 * @描述：类
 */

public class Constant {

    //浏览器用户代理
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
    //vpn本地代理地址和端口号
    public static final String PROXY_HOST = "172.16.1.21";
    public static final int PROXY_PORT = 2443;
    //爬取深度
    public static final int DEEPTH = 1;
    //爬虫前睡眠时间最大值
    public static final int SLEEP_BOUND = 30000;

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
