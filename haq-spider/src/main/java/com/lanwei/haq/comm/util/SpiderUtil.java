package com.lanwei.haq.comm.util;

import com.lanwei.haq.comm.thread.ResourceManager;
import com.lanwei.haq.spider.dao.web.MysqlDao;
import com.lanwei.haq.spider.entity.NewsEntity;
import com.lanwei.haq.spider.entity.web.SubjectEntity;
import com.lanwei.haq.spider.entity.web.WebClassEntity;
import com.lanwei.haq.spider.entity.web.WebConfigEntity;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者：刘新运
 * @日期：2017/7/2 20:59
 * @描述：类
 */
@Component
public class SpiderUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpiderUtil.class);

    private RedisUtil redisUtil;
    private MysqlDao mysqlDao;
    private ResourceManager resourceManager;

    @Autowired
    public SpiderUtil(RedisUtil redisUtil, MysqlDao mysqlDao, ResourceManager resourceManager) {
        this.redisUtil = redisUtil;
        this.mysqlDao = mysqlDao;
        this.resourceManager = resourceManager;
    }

    /**
     * 开始所有网站爬虫任务
     */
    public void spiderAll() {
        //查询数据库中配置
        Map<String, Integer> config = getCurrentConfig();
        int cron = config.get("cron");//间隔时间分钟
        int threadCount = config.get("thread");//线程数
        List<Integer> list = mysqlDao.getAllWebId();
        for (int webId : list) {
            resourceManager.setWebSpiderThreadAndStart(webId, threadCount, cron);
        }
    }
    /**
     * 开始指定id网站爬虫任务
     * @param webId 
     */
    public void spiderByWebId(int webId) {
        //查询数据库中配置
        Map<String, Integer> config = getCurrentConfig();
        int cron = config.get("cron");//间隔时间分钟
        int threadCount = config.get("thread");//线程数
        resourceManager.setWebSpiderThreadAndStart(webId, threadCount, cron);
    }

    /**
     * 获得当前配置
     * @return
     */
    private Map<String, Integer> getCurrentConfig(){
        Map<String, Integer> map = new HashMap<>();
        List<WebConfigEntity> list = mysqlDao.getCurrentConfig();
        for (WebConfigEntity webConfigEntity : list){
            if (webConfigEntity.getType() == 1){
                map.put("cron", webConfigEntity.getData());
            }else if (webConfigEntity.getType() == 2){
                map.put("thread", webConfigEntity.getData());
            }
        }
        return map;
    }

    /**
     * 根据html获取所有子链接
     *
     * @param doc
     * @return
     * @throws IOException
     */
    public static List<String> getLinks(Document doc) {
        List<String> list = new ArrayList<>();
        if (doc.select("a[href]") != null && !doc.select("a[href]").isEmpty()) {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String subUrl = link.attr("abs:href");
                if (null != subUrl && !"".equals(subUrl)) {
                    int index = subUrl.indexOf("#");
                    if (index > -1) {
                        subUrl = subUrl.substring(0, index);
                    }
                    list.add(subUrl);
                }
            }
        }
        return list;
    }

    /**
     * 链接正则匹配
     *
     * @param input
     * @return
     */
    public static boolean matchUrl(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 获取新闻标题和内容，所属分类和专题
     *
     * @param doc
     * @return
     */
    public NewsEntity getNewsByDoc(Document doc, WebEntity webEntity) {
        Elements titleElements = doc.select(webEntity.getTitleSelect());
        Elements contentElements = doc.select(webEntity.getContentSelect());
        if (titleElements==null || titleElements.isEmpty() ||
                contentElements==null || contentElements.isEmpty()){
            return null;
        }
        //统计用
        Jedis statisJedis = redisUtil.getJedis(Constant.REDIS_STATIS_INDEX);
        long hour = DateUtil.getCurrentHour();
        NewsEntity result = new NewsEntity();
        String titleResult = titleElements.first().text();//标题
        String contentResult = contentElements.first().html();//带HTML标签的内容
        result.setTitle(titleResult);
        result.setContent(contentResult);
        //接下来获取新闻所属专题
        StringBuilder sb = new StringBuilder(" ");
        String content = contentElements.first().text();//纯文本内容
        List<SubjectEntity> subjectEntities = mysqlDao.getAllSubject();
        for (SubjectEntity subjectEntity : subjectEntities) {
            Pattern pattern = Pattern.compile(subjectEntity.getKeywords());
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                sb.append(subjectEntity.getId()).append(" ");
                //统计专题
                statisJedis.hincrBy(Constant.REDIS_SUBJECT_PREFIX + subjectEntity.getId(), String.valueOf(hour), 1);
            }
        }
        String subject = sb.toString().trim();//去除前后空格
        result.setSubject(subject);
        //接下来获取新闻分类
        Integer classify = Constant.NewsClass.OTHER;//默认分类其他
        List<WebClassEntity> webClassEntities = mysqlDao.getClassByWebId(webEntity.getId());
        for (WebClassEntity webClassEntity : webClassEntities) {
            Elements elements = doc.select(webClassEntity.getClassSelect());
            if (elements==null || elements.isEmpty()){
                continue;
            }
            String temp = elements.first().text();
            //能找到分类就进行比较，看该新闻的分类和数据库里存的原分类名称是否一致
            if (temp.indexOf(webClassEntity.getSourceName()) >= 0) {
                classify = webClassEntity.getClassId();
                break;
            }
        }
        result.setClassify(classify);
        //统计分类
        statisJedis.hincrBy(Constant.REDIS_CLASS_PREFIX + classify, String.valueOf(hour), 1);
        redisUtil.close(statisJedis);
        return result;
    }

}
