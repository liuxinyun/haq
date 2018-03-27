package com.lanwei.haq.spider.listener;

import com.lanwei.haq.comm.thread.DownloadImage;
import com.lanwei.haq.comm.thread.ResourceManager;
import com.lanwei.haq.comm.util.SpiderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 初始化系统监听器，当Application初始化成功或者刷新的时候会执行该方法
 *
 * @author liuxinyun
 * @date 2017/1/8 23:23
 */
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger log = LoggerFactory.getLogger(InitListener.class);

    @Autowired
    private SpiderUtil spiderUtil;
    @Autowired
    private DownloadImage downloadImage;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //root application context 没有parent.
        if(event.getApplicationContext().getParent() == null){
            log.info("spring初始化完成");
            try {
                ResourceManager.init();
                log.info("开始爬虫");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        spiderUtil.spiderAll();
                    }
                }).start();
                log.info("开始下载图片");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadImage.download();
                    }
                }).start();
            } catch (Exception e) {
                log.error("系统初始化失败："+e.getMessage(), e);
            }
        }

    }

}
