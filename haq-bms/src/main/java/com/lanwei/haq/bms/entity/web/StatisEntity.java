package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * @description：统计实体
 * @author：liuxinyun
 * @date：2018/1/11 23:10
 */
public class StatisEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -6127983270858818302L;

    /**
     * 网址
     */
    private String url;
    /**
     * 失败数
     */
    private int failCount;
    /**
     * 链接总数
     */
    private int totalCount;
    /**
     * 距离今天的日期，默认0表示今天，1表示昨天。。
     */
    private int dayNum = 0;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    @Override
    public String toString() {
        return "StatisEntity{" +
                "url='" + url + '\'' +
                ", failCount=" + failCount +
                ", totalCount=" + totalCount +
                '}';
    }
}
