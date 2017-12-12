package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：类
 */

public class WebEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5113195625614870736L;

    /**
     * 网站名称
     */
    private String webname;

    /**
     * 网站地址
     */
    private String weburl;
    /**
     * 种子网址
     */
    private String seedUrls;
    /**
     * 标题选择器
     */
    private String titleSelect;
    /**
     * 内容选择器
     */
    private String contentSelect;
    /**
     * 网站的正则表达式
     */
    private String regex;
    /**
     * 所属区域id
     */
    private int areaId;
    /**
     * 所属区域名称
     */
    private String areaName;

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getSeedUrls() {
        return seedUrls;
    }

    public void setSeedUrls(String seedUrls) {
        this.seedUrls = seedUrls;
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname;
    }

    public String getTitleSelect() {
        return titleSelect;
    }

    public void setTitleSelect(String titleSelect) {
        this.titleSelect = titleSelect;
    }

    public String getContentSelect() {
        return contentSelect;
    }

    public void setContentSelect(String contentSelect) {
        this.contentSelect = contentSelect;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "WebEntity{" +
                "webname='" + webname + '\'' +
                ", weburl='" + weburl + '\'' +
                ", seedUrls='" + seedUrls + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", contentSelect='" + contentSelect + '\'' +
                ", regex='" + regex + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                "} " ;
    }
}
