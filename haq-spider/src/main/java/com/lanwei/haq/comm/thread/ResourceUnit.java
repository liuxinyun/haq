package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.entity.UrlDepth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Carlisle
 */
public class ResourceUnit {

    public int webId = -1;
    public int tnum = 0;
    public ThreadPoolExecutor threadpool = null;
    public Map<Integer,ConcurrentLinkedQueue<UrlDepth>> queueMap = null;

    public ResourceUnit(int webId, int tnum) {
        this.webId = webId;
        this.queueMap = new HashMap<>();
        this.tnum = tnum;
        this.threadpool = new ThreadPoolExecutor(tnum, 2 * tnum, 30, TimeUnit.MINUTES, new LinkedBlockingDeque<>(10));
    }
}
