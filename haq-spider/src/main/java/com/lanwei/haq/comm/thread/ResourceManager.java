package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.entity.SpiderConfig;
import com.lanwei.haq.comm.entity.UrlDepth;
import com.lanwei.haq.comm.jdbc.MyJedisService;
import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.EsUtil;
import com.lanwei.haq.comm.util.SpiderUtil;
import com.lanwei.haq.spider.dao.web.MysqlDao;
import com.lanwei.haq.spider.entity.web.WebEntity;
import com.lanwei.haq.spider.entity.web.WebSeedEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 爬虫资源管理：定时器、线程池、数据buffer Created by Carlisle on 2017/7/10.
 */
@Component
public class ResourceManager {

    @Autowired
    private SpiderUtil spiderUtil;
    @Autowired
    private MysqlDao mysqlDao;
    @Autowired
    private EsUtil esUtil;
    @Resource
    private MyJedisService saveJedisService;
    @Resource
    private MyJedisService statisJedisService;

    private static final int MAX_QUEUE_SIZE = 2000;

    private static Map<Integer, Timer> timers = new HashMap<>();
    private static Map<Integer, ResourceUnit> resource = new HashMap<>();

    private static Timer monitorTimer = new Timer("资源监测线程");
    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    public static void init()  {
        // 每3分钟检测一次
        monitorTimer.scheduleAtFixedRate(new ResourceMonitor(), 0, 3*60*1000);
    }

    /**
     * 根据网站id删除相应的爬虫线程
     * @param webIds
     * @return
     */
    public void removeThreadByWebIds(final List<Integer> webIds){
        Timer timer = null;
        for (Integer webId : webIds) {
            if (resource.containsKey(webId)){
                timer = timers.get(webId);
                timer.cancel();
                timer = null;
                resource = dealMapRemove(resource, webId);
                logger.error("Website id:{} has removed.", webId);
            }
        }
    }

    /**
     * 根据传入的网站id和配置信息进行爬虫
     * @param webIds
     * @param spiderConfig
     */
    public void setWebSpiderThreadAndStart(List<Integer> webIds, final SpiderConfig spiderConfig){
        int tnum = spiderConfig.getThreadNum();// 线程数
        int cronTime = spiderConfig.getCron();// 间隔时间
        ResourceUnit unit = null;
        Timer timer = null;
        for (Integer webId : webIds) {
            logger.info("Receive request to start spider for website:" + webId + ",thread num:" + tnum);
            // 如果传入的网站id爬虫从未运行过，给该网站id分配资源
            if (!resource.containsKey(webId)) {
                logger.info("Website:" + webId + " is new, start to allocate resource...");
                unit = new ResourceUnit(webId, tnum);
                resource.put(webId, unit);
                logger.info("Website:" + webId + "resource allocated.");
            } else {
                // 如果传入的网站id爬虫正在执行，获取其资源
                logger.info("Website:" + webId + " is running, start to refresh...");
                unit = resource.get(webId);
                timer = timers.get(webId);
                //取消当前调度
                timer.cancel();
                timer = null;
            }
            timer = new Timer("Timer-webId-"+webId);
            timers.put(webId, timer);
            timer.scheduleAtFixedRate(new SpiderUnit(unit), 0, cronTime*60*1000);
            logger.info("Website id:" + webId + "spider task scheduled at rate of " + cronTime + "min.");
        }
    }

    //用于检查各队列容量，以动态调整线程数量
    private static class ResourceMonitor extends TimerTask {

        ResourceMonitor() {

        }

        @Override
        public void run() {

            logger.info("============= ResourceManager:Queue Monitor ================");
            logger.info("     website       queue size      active thread");
            for (Entry<Integer, ResourceUnit> e : resource.entrySet()) {
                int webId = e.getKey();
                ResourceUnit unit = e.getValue();
                int size = 0;
                for (Entry<Integer, ConcurrentLinkedQueue<UrlDepth>> entry : unit.queueMap.entrySet()) {
                    size += entry.getValue().size();
                }
                logger.info("        " + webId + "              " + size + "                 " + unit.threadpool.getActiveCount());
                if (size > MAX_QUEUE_SIZE) {
                    //do something
                }
            }
            logger.info("============================================================");

        }
    }

    /**
     * 处理map删除操作
     * @param source
     * @param webId
     * @return
     */
    private Map<Integer, ResourceUnit> dealMapRemove(Map<Integer, ResourceUnit> source, Integer webId){
        Map<Integer, ResourceUnit> result = source;
        Iterator<Entry<Integer, ResourceUnit>> it = result.entrySet().iterator();
        while (it.hasNext()){
            Entry<Integer, ResourceUnit> entry = it.next();
            if (entry.getKey().equals(webId)){
                it.remove();
            }
        }
        return result;
    }

    /**
     * 爬虫单元
     */
    private class SpiderUnit extends TimerTask {

        private ResourceUnit unit;

        public SpiderUnit(ResourceUnit unit) {
            this.unit = unit;
        }

        @Override
        public void run() {
            logger.info("Website " + unit.webId + " putting seed to Queue...");
            WebEntity webEntity = mysqlDao.getWebById(unit.webId);
            List<WebSeedEntity> webSeeds = mysqlDao.getSeedByWebId(unit.webId);
            if (CollectionUtils.isEmpty(webSeeds)){
                // 没有种子网址，那就用该网站的
                WebSeedEntity webSeedEntity = new WebSeedEntity();
                webSeedEntity.setId(0);
                webSeedEntity.setWebName(webEntity.getWebname());
                webSeedEntity.setSeedurl(webEntity.getWeburl());
                webSeedEntity.setRegex(webEntity.getRegex());
                webSeedEntity.setTitleSelect(webEntity.getTitleSelect());
                webSeedEntity.setContentSelect(webEntity.getContentSelect());
                webSeedEntity.setClassId(Constant.NEWS_CLASS_OTHER);
                webSeeds.add(webSeedEntity);
            }
            for (WebSeedEntity webSeed : webSeeds) {
                // TODO 一个种子网址只放一个线程
                webSeed.setWebName(webEntity.getWebname());// 存es使用
                webSeed.setAreaId(webEntity.getAreaId());// 存所属地域使用
                ConcurrentLinkedQueue<UrlDepth> queue = new ConcurrentLinkedQueue<>();
                queue.add(new UrlDepth(webSeed.getSeedurl(),0));
                unit.queueMap.put(webSeed.getId(), queue);
                unit.threadpool.execute(new Spider(webSeed, queue, spiderUtil, esUtil, saveJedisService, statisJedisService));
            }
            logger.info("Website " + unit.webId + " started "+unit.tnum+" threads.");
        }

    }

}
