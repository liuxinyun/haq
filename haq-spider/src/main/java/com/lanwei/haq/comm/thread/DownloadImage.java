package com.lanwei.haq.comm.thread;

import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.comm.entity.ImgPath;
import com.lanwei.haq.comm.jdbc.MyJedisService;
import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.DownPicUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description：
 * @author：liuxinyun
 * @date：2018/3/27 22:30
 */
@Component
public class DownloadImage {

    private static final Logger log = LoggerFactory.getLogger(DownloadImage.class);

    @Resource
    private MyJedisService statisJedisService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    public void download(){
        while (true){
            // 设置三秒超时，如果三秒后还为空，则等待3分钟再重试
            List<String> brpop = statisJedisService.brpop(3, Constant.REDIS_IMG_INFO);
            if (CollectionUtils.isEmpty(brpop)){
                try {
                    Thread.sleep(3*60*1000);
                } catch (InterruptedException e) {
                    log.error("下载图片线程中断", e);
                }
            }else {
                String imgInfo = brpop.get(1);
                ImgPath path = JSONObject.parseObject(imgInfo, ImgPath.class);
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DownPicUtil.saveImgToLocal(path.getSource(), path.getLocal(), path.getName());
                    }
                });
            }
        }
    }
}
