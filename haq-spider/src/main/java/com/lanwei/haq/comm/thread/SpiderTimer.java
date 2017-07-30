/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lanwei.haq.comm.thread;

import com.lanwei.haq.spider.service.web.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 *
 * @author Carlisle
 */
@Component
public class SpiderTimer extends TimerTask {

    @Autowired
    private WebService webService;

    private ResourceUnit unit = null;
    private final Logger logger = LoggerFactory.getLogger(SpiderTimer.class);
    
    SpiderTimer(ResourceUnit unit) {
        this.unit = unit;
    }
    
    @Override
    public void run() {
        logger.info("Website " + unit.websiteId + " putting seed to Queue...");
        for (int i = 0; i < unit.tnum; i++) {
            unit.q.add(webService.getWebById(unit.websiteId).getWeburl());
            unit.threadpool.execute(new Spider(webService.getWebById(unit.websiteId), unit.q));
        }
        logger.info("Website " + unit.websiteId + " started "+unit.tnum+" threads.");
    }
}
