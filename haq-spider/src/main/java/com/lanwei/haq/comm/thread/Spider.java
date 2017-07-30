package com.lanwei.haq.comm.thread;

import com.alibaba.fastjson.JSON;
import com.lanwei.haq.comm.util.*;
import com.lanwei.haq.spider.entity.NewsEntity;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @作者：刘新运
 * @日期：2017/6/5 22:41
 * @描述：类
 */
@Component
public class Spider implements Runnable {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SpiderUtil spiderUtil;
    @Autowired
    private EsUtil esUtil;

    private final WebEntity webEntity;

    //存储衍生网址
    private Queue<String> queue;
    static Logger logger = LoggerFactory.getLogger(Spider.class);

    public Spider(WebEntity webEntity, Queue<String> queue) {
        this.webEntity = webEntity;
        this.queue = queue;
    }

    @Override
    public void run() {
        Jedis saveJedis = redisUtil.getJedis(Constant.REDIS_SAVE_INDEX);
        //统计
        Jedis statisJedis = redisUtil.getJedis(Constant.REDIS_STATIS_INDEX);
        String key = Constant.REDIS_WEB_PREFIX + WebUtil.getHost(webEntity.getWeburl());
        while (queue != null && !queue.isEmpty()) {
            if (queue.isEmpty()) {
                break;
            }
            String weburl = queue.poll();
            logger.info("Getting " + weburl);
            try {
                Document document = Jsoup.connect(weburl).timeout(3000).get();
                if (document != null) {
                    long hour = DateUtil.getCurrentHour();
                    //网站统计自增1
                    statisJedis.hincrBy(key, String.valueOf(hour), 1);
                    //总量统计增1
                    statisJedis.hincrBy(Constant.REDIS_TOTAL_PREFIX, String.valueOf(hour), 1);
                    //地域统计总量增1
                    statisJedis.hincrBy(Constant.REDIS_AREA_PREFIX + webEntity.getAreaId(), String.valueOf(hour), 1);
                    //获取所有未爬取子链接
                    List<String> list = spiderUtil.getLinks(document);
                    for (String s : list) {
                        //符合正则表达式 && 在redis不存在 ==> 入Queue
                        if (spiderUtil.matchUrl(webEntity.getRegex(), s)) {
                            if (saveJedis.setnx(s, Long.toString(System.currentTimeMillis())) == 1) {
                                queue.add(s);
                            }
                        }
                    }
                    //如果不符合正则表达式就不进行标题内容获取了
                    if (!spiderUtil.matchUrl(webEntity.getRegex(), weburl)) {
                        continue;
                    }
                    //获取新闻标题和内容，所属分类和专题。
                    NewsEntity news = spiderUtil.getNewsByDoc(document, webEntity);
                    String content = news.getContent();
                    //根据HTML内容替换图片地址为本服务器地址
                    Map<String, Object> map = DownPicUtil.htmlToFtp(content);
                    news.setContent(map.get("html").toString());//替换后的内容
                    String imgpath = JSON.toJSONString(map.get("list"));
                    news.setImg_path(imgpath);//图片地址
                    //填充其他属性
                    news.setWebsite(webEntity.getWebname());
                    news.setUrl(weburl);
                    news.setDatetime(DateUtil.format(new Date()));
                    esUtil.insertObject("haqqq", "news_test", news);
                    logger.info(weburl + " stored into Elasticsearch.");
                }
            } catch (Exception e) {
                logger.error("Get " + weburl + " failed for ", e);
            }
        }
        redisUtil.close(saveJedis);
        redisUtil.close(statisJedis);
        logger.info("Website "+webEntity.getId()+" spider thread done.");
    }

}
