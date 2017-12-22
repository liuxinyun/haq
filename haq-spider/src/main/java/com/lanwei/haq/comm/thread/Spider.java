package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.entity.UrlDepth;
import com.lanwei.haq.comm.jdbc.MyJedisService;
import com.lanwei.haq.comm.util.*;
import com.lanwei.haq.spider.entity.NewsEntity;
import com.lanwei.haq.spider.entity.web.WebSeedEntity;
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

    private WebSeedEntity webSeedEntity;
    private Queue<UrlDepth> queue;//存储衍生网址
    private SpiderUtil spiderUtil;
    private EsUtil esUtil;
    private MyJedisService saveJedis;
    private MyJedisService statisJedis;

    public Spider(WebSeedEntity webSeedEntity, Queue<UrlDepth> queue,
                  SpiderUtil spiderUtil, EsUtil esUtil,
                  MyJedisService saveJedis, MyJedisService statisJedis) {
        this.webSeedEntity = webSeedEntity;
        this.queue = queue;
        this.spiderUtil = spiderUtil;
        this.esUtil = esUtil;
        this.saveJedis = saveJedis;
        this.statisJedis = statisJedis;
    }

    @Override
    public void run() {
        String websiteKey = Constant.REDIS_WEB_PREFIX + WebUtil.getDomain(webSeedEntity.getSeedurl());
        //每个链接可爬取的最大子链接数量
        final int maxCount = PropertiesUtil.getInt("spider.max");
        while (queue!=null && !queue.isEmpty()) {
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
                        .proxy(Constant.getProxy())
                        .userAgent(Constant.USER_AGENT)
                        .header("User-Agent", Constant.USER_AGENT)
                        .ignoreContentType(false)//解析响应是忽略文档类型
                        .ignoreHttpErrors(false)  //响应时是否忽略错误，404等
                        .validateTLSCertificates(false)//关闭证书验证
                        .timeout(12000).get();
            } catch (IOException e) {
                saveJedis.del(weburl);//出现异常未爬取的网址从redis中删除，下次还可以再爬
                logger.error("Get " + weburl + " failed for ", e);
            }
            if (document != null) {
                //未达到指定深度，继续爬子连接
                if(depth < PropertiesUtil.getInt("spider.depth")){
                    //获取所有未爬取子链接
                    Set<String> set = SpiderUtil.getLinks(document);
                    //每个子链接只取前maxCount个有效的进行进一步爬取,total<=maxCount
                    int total = 0;
                    for (String s : set) {
                        //符合正则表达式 && 在redis不存在 ==> 入Queue
                        if (SpiderUtil.matchUrl(webSeedEntity.getRegex(), s)) {
                            if (saveJedis.setnx(s, Long.toString(System.currentTimeMillis())) == 1) {
                                queue.add(new UrlDepth(s, depth+1));
                                if (++total > maxCount){
                                    break;
                                }
                            }
                        }
                    }
                }
                //如果是种子网址就不进行下面的操作了
                if (weburl.equals(webSeedEntity.getSeedurl())){
                    continue;
                }
                //如果不符合正则表达式就不进行标题内容获取了
                if (!SpiderUtil.matchUrl(webSeedEntity.getRegex(), weburl)) {
                    logger.warn("url={} is not satisfy regex.", weburl);
                    continue;
                }
                //获取新闻标题和内容，所属分类和专题。
                NewsEntity news = spiderUtil.getNewsByDoc(document, webSeedEntity);
                if (news == null){
                    logger.warn("url={} get news is null for titleSelect={} and contentSelect={}",
                            weburl, webSeedEntity.getTitleSelect(), webSeedEntity.getContentSelect());
                    continue;//当标签选择器跟该网址对应不上时获取不到标题内容等，认为不是所要的新闻。
                }
                String content = news.getContent();
                //根据HTML内容替换图片地址为本服务器地址
                Map<String, String> map = DownPicUtil.htmlToFtp(content);
                news.setContent(map.get("html"));//替换后的内容
                news.setImg_path(map.get("img"));//图片地址
                //填充其他属性
                news.setWebsite(webSeedEntity.getWebName());
                news.setUrl(weburl);
                news.setDatetime(DateUtil.format(new Date()));
                esUtil.insertObject("haqqq", "news_test", news);
                //logger.info("news={} stored into Elasticsearch.", news.toString());
                //下面开始进行统计
                long hour = DateUtil.getCurrentHour();
                //网站统计自增1
                statisJedis.hincrBy(websiteKey, String.valueOf(hour), 1);
                //总量统计增1
                statisJedis.hincrBy(Constant.REDIS_TOTAL_PREFIX, String.valueOf(hour), 1);
                //地域统计总量增1
                statisJedis.hincrBy(Constant.REDIS_AREA_PREFIX + webSeedEntity.getAreaId(), String.valueOf(hour), 1);
            }
        }
    }

}
