/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.util.EsUtil;
import com.lanwei.haq.comm.util.RedisUtil;
import com.lanwei.haq.comm.util.SpiderUtil;
import com.lanwei.haq.spider.entity.web.WebEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 *
 * @author Carlisle
 */
public class SpiderTimer extends TimerTask {

    private final Logger logger = LoggerFactory.getLogger(SpiderTimer.class);

    private WebEntity webEntity;
    private ResourceUnit unit;
    private RedisUtil redisUtil;
    private SpiderUtil spiderUtil;
    private EsUtil esUtil;

    public SpiderTimer(WebEntity webEntity, ResourceUnit unit,
                       RedisUtil redisUtil, SpiderUtil spiderUtil,
                       EsUtil esUtil) {
        this.webEntity = webEntity;
        this.unit = unit;
        this.redisUtil = redisUtil;
        this.spiderUtil = spiderUtil;
        this.esUtil = esUtil;
    }
    
    @Override
    public void run() {
        logger.info("Website " + unit.websiteId + " putting seed to Queue...");
        for (int i = 0; i < unit.tnum; i++) {
            unit.q.add(webEntity.getWeburl());
            unit.threadpool.execute(new Spider(webEntity, unit.q, redisUtil, spiderUtil, esUtil));
        }
        logger.info("Website " + unit.websiteId + " started "+unit.tnum+" threads.");
    }
}
