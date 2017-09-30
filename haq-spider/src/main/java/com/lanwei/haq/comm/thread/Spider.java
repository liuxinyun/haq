package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.entity.UrlDepth;
import com.lanwei.haq.comm.util.*;
import com.lanwei.haq.spider.entity.NewsEntity;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

/**
 * @作者：刘新运
 * @日期：2017/6/5 22:41
 * @描述：类
 */
public class Spider implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(Spider.class);

    private WebEntity webEntity;
    private Queue<UrlDepth> queue;//存储衍生网址
    private RedisUtil redisUtil;
    private SpiderUtil spiderUtil;
    private EsUtil esUtil;

    public Spider(WebEntity webEntity, Queue<UrlDepth> queue,
                  RedisUtil redisUtil, SpiderUtil spiderUtil,
                  EsUtil esUtil) {
        this.webEntity = webEntity;
        this.queue = queue;
        this.redisUtil = redisUtil;
        this.spiderUtil = spiderUtil;
        this.esUtil = esUtil;
    }

    @Override
    public void run() {
        Jedis saveJedis = redisUtil.getJedis(Constant.REDIS_SAVE_INDEX);
        //统计
        Jedis statisJedis = redisUtil.getJedis(Constant.REDIS_STATIS_INDEX);
        String key = Constant.REDIS_WEB_PREFIX + WebUtil.getHost(webEntity.getWeburl());
        int maxCount = PropertiesUtil.getInt("spider.max");//每次爬取的最大数量
        int total = 0;//本次已经爬取的总数，不能比maxCount大
        while (total<=maxCount && queue!=null && !queue.isEmpty()) {
            if (queue.isEmpty()) {
                break;
            }
            UrlDepth urlDepth = queue.poll();
            String weburl = urlDepth.getUrl();
            try {
                Thread.sleep(new Random().nextInt(PropertiesUtil.getInt("spider.sleep.bound"))+1000);
            } catch (InterruptedException e) {
                logger.error("Get " + weburl + " failed for InterruptedException ", e);
            }
            int depth = urlDepth.getDepth();//该网址所在的深度
            Document document = null;
            try {
                document = Jsoup.connect(weburl)
                        .proxy(PropertiesUtil.get("proxy.host"), PropertiesUtil.getInt("proxy.port"))
                        .ignoreContentType(false)//解析响应是忽略文档类型
                        .ignoreHttpErrors(false)  //响应时是否忽略错误，404等
                        .validateTLSCertificates(false)//关闭证书验证
                        .timeout(10000).get();
            } catch (IOException e) {
                saveJedis.del(weburl);//出现异常未爬取的网址从redis中删除，下次还可以再爬
                logger.error("Get " + weburl + " failed for ", e);
            }
            if (document != null) {
                //未达到指定深度，继续爬子连接
                if(depth < PropertiesUtil.getInt("spider.depth")){
                    //获取所有未爬取子链接
                    List<String> list = SpiderUtil.getLinks(document);
                    for (String s : list) {
                        total++;
                        if (total > maxCount){
                            break;//达到最大数量后就不放入队列了
                        }
                        //符合正则表达式 && 在redis不存在 ==> 入Queue
                        /*if (SpiderUtil.matchUrl(webEntity.getRegex(), s)) {
                            if (saveJedis.setnx(s, Long.toString(System.currentTimeMillis())) == 1) {
                                queue.add(new UrlDepth(s, depth+1));
                            }
                        }*/
                    }
                }
                //如果不符合正则表达式就不进行标题内容获取了
                if (!SpiderUtil.matchUrl(webEntity.getRegex(), weburl)) {
                    continue;
                }
                //获取新闻标题和内容，所属分类和专题。
                NewsEntity news = spiderUtil.getNewsByDoc(document, webEntity);
                if (news == null){
                    continue;//当标签选择器跟该网址对应不上时获取不到标题内容等，认为不是所要的新闻。
                }
                String content = news.getContent();
                //根据HTML内容替换图片地址为本服务器地址
                Map<String, String> map = DownPicUtil.htmlToFtp(content);
                news.setContent(map.get("html"));//替换后的内容
                news.setImg_path(map.get("img"));//图片地址
                //填充其他属性
                news.setWebsite(webEntity.getWebname());
                news.setUrl(weburl);
                news.setDatetime(DateUtil.format(new Date()));
                //esUtil.insertObject("haqqq", "news_test", news);
                logger.info("news={} stored into Elasticsearch.", news.toString());
                //下面开始进行统计
                long hour = DateUtil.getCurrentHour();
                //网站统计自增1
                statisJedis.hincrBy(key, String.valueOf(hour), 1);
                //总量统计增1
                statisJedis.hincrBy(Constant.REDIS_TOTAL_PREFIX, String.valueOf(hour), 1);
                //地域统计总量增1
                statisJedis.hincrBy(Constant.REDIS_AREA_PREFIX + webEntity.getAreaId(), String.valueOf(hour), 1);
            }
        }
        redisUtil.close(saveJedis);
        redisUtil.close(statisJedis);
    }

}
