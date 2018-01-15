package com.lanwei.haq.comm.util;

/**
 * @作者：刘新运
 * @日期：2017/7/9 10:39
 * @描述：类
 */

public class Constant {

    /**
     * 爬虫地址
     */
    public static final String SPIDER_ADDRESS = "http://172.16.1.12:28081/spider/spider/now";

    /**
     * 爬虫测试地址
     */
    public static final String SPIDER_TEST = "http://172.16.1.12:28081/spider/spider/test";

    /**
     * 单个网页爬虫测试地址
     */
    public static final String SPIDER_URL_TEST = "http://172.16.1.12:28081/spider/spider/url/test";

    // 保存每个种子网站当天的链接总数total_20180111
    public static final String REDIS_TOTAL = "total_";
    // 保存每个种子网站当天的爬取失败数fail_20180111
    public static final String REDIS_FAIL = "fail_";

}
