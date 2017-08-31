/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lanwei.haq.comm.thread;

import com.lanwei.haq.comm.entity.UrlDeepth;

import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Carlisle
 */
public class ResourceUnit {

    public int websiteId = -1;
    public int tnum = 0;
    public ThreadPoolExecutor threadpool = null;
    public ConcurrentLinkedQueue<UrlDeepth> q = null;

    public ResourceUnit(int websiteId, int tnum) {
        this.websiteId = websiteId;
        this.q = new ConcurrentLinkedQueue<>();
        this.tnum = tnum;
        this.threadpool = new ThreadPoolExecutor(tnum, 2 * tnum, 30, TimeUnit.MINUTES, new LinkedBlockingDeque<>(10));
    }
}
