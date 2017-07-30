package com.lanwei.haq.comm.util;

import com.lanwei.haq.comm.thread.ResourceManager;
import com.lanwei.haq.spider.entity.NewsEntity;
import com.lanwei.haq.spider.entity.web.SubjectEntity;
import com.lanwei.haq.spider.entity.web.WebClassEntity;
import com.lanwei.haq.spider.entity.web.WebEntity;
import com.lanwei.haq.spider.service.web.SubjectService;
import com.lanwei.haq.spider.service.web.WebClassService;
import com.lanwei.haq.spider.service.web.WebConfigService;
import com.lanwei.haq.spider.service.web.WebService;
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

    private final WebService webService;
    private final WebClassService webClassService;
    private final WebConfigService webConfigService;
    private final SubjectService subjectService;
    private final RedisUtil redisUtil;

    @Autowired
    public SpiderUtil(WebService webService, WebClassService webClassService,
                      WebConfigService webConfigService, SubjectService subjectService,
                      RedisUtil redisUtil) {
        this.webService = webService;
        this.webClassService = webClassService;
        this.webConfigService = webConfigService;
        this.subjectService = subjectService;
        this.redisUtil = redisUtil;
    }

    /**
     * 开始所有网站爬虫任务
     */
    public void spiderAll() {
        //查询数据库中配置
        Map<String, Integer> config = webConfigService.getCurrentConfig();
        int cron = config.get("cron");//间隔时间分钟
        int threadCount = config.get("thread");//线程数
        List<Integer> list = webService.getAllWebId();
        for (int webId : list) {
            ResourceManager.setWebSpiderThreadAndStart(webId, threadCount, cron);
        }
    }
    /**
     * 开始指定id网站爬虫任务
     * @param webId 
     */
    public void spiderByWebId(int webId) {
        //查询数据库中配置
        Map<String, Integer> config = webConfigService.getCurrentConfig();
        int cron = config.get("cron");//间隔时间分钟
        int threadCount = config.get("thread");//线程数
        ResourceManager.setWebSpiderThreadAndStart(webId, threadCount, cron);
    }

    /**
     * 根据html获取所有子链接
     *
     * @param doc
     * @return
     * @throws IOException
     */
    public List<String> getLinks(Document doc) throws IOException {
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
    public boolean matchUrl(String regex, String input) {
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
        //统计用
        Jedis statisJedis = redisUtil.getJedis(Constant.REDIS_STATIS_INDEX);
        long hour = DateUtil.getCurrentHour();
        NewsEntity result = new NewsEntity();
        String titleResult = doc.select(webEntity.getTitleSelect()).first().text();//标题
        String contentResult = doc.select(webEntity.getContentSelect()).first().html();//带HTML标签的内容
        result.setTitle(titleResult);
        result.setContent(contentResult);
        //接下来获取新闻所属专题
        StringBuilder sb = new StringBuilder(" ");
        String content = doc.select(webEntity.getContentSelect()).text();
        List<SubjectEntity> subjectEntities = subjectService.getAllSubject();
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
        List<WebClassEntity> webClassEntities = webClassService.getClassByWebId(webEntity.getId());
        for (WebClassEntity webClassEntity : webClassEntities) {
            Elements elements = doc.select(webClassEntity.getClassSelect());
            if (!elements.isEmpty()) {
                String temp = elements.first().text();
                //能找到分类就进行比较，看该新闻的分类和数据库里存的原分类名称是否一致
                if (temp.indexOf(webClassEntity.getSourceName()) >= 0) {
                    classify = webClassEntity.getClassId();
                    break;
                }
            }
        }
        result.setClassify(classify);
        //统计分类
        statisJedis.hincrBy(Constant.REDIS_CLASS_PREFIX + classify, String.valueOf(hour), 1);

        redisUtil.close(statisJedis);
        return result;
    }

}
