package com.lanwei.haq.bms.listener;

import com.lanwei.haq.comm.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Objects;

/**
 * 初始化系统监听器，当Application初始化成功或者刷新的时候会执行该方法
 *
 * @author liuxinyun
 * @date 2017/1/8 23:23
 */
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger log = LoggerFactory.getLogger(InitListener.class);

    /**
     * 需要执行初始化的ip
     */
    private String ip;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext context = event.getApplicationContext();
        // * context的id，org.springframework.web.context.WebApplicationContext: 是spring的id
        // * org.springframework.web.context.WebApplicationContext:/spring 是spring mvc的id
        // * 容器初始化的时候首先会触发spring的初始化成功监听，其次执行spring mvc初始化成功监听
        String[] idArr = {"org.springframework.web.context.WebApplicationContext:", "org.springframework.web.context.WebApplicationContext:/spring"};
        String id = context.getId();
        // 当触发spring mvc初始化成功监听是再执行系统参数的初始化
        if (idArr[1].equals(id)) {
            log.info("Application初始化完毕，开始准备初始化系统，本机ip:[{}]，允许执行初始化的ip:[{}]", IpUtil.getRealIp(), ip);

            /// 有可能系统会部署到多台服务器，通过限制ip的方式，限制只在其中某一台服务器执行初始化。
            if (Objects.equals(IpUtil.getRealIp(), ip)) {
                /// TODO 需要初始化系统参数
                log.info("[ip:{}]允许执行，开始初始化redis缓存", ip);







                log.info("[ip:{}]初始化系统完毕！！！", ip);
            }

        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
