package com.lanwei.haq.comm.entity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/10/7 23:27
 * @描述：类
 */

public class SpiderConfig implements Serializable {
    private static final long serialVersionUID = 4455378163058836737L;

    /**
     * 线程数
     */
    private Integer threadNum;
    /**
     * 间隔时间
     */
    private Integer cron;

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getCron() {
        return cron;
    }

    public void setCron(Integer cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "SpiderConfig{" +
                "threadNum=" + threadNum +
                ", cron=" + cron +
                '}';
    }
}
