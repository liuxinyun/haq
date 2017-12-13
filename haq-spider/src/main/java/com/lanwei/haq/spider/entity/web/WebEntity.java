package com.lanwei.haq.spider.entity.web;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：类
 */

public class WebEntity implements Serializable{

    private static final long serialVersionUID = -6960156273140862317L;

    /**
     * 网站id
     */
    private Integer id;
    /**
     * 网站名称
     */
    private String webname;

    /**
     * 网站地址
     */
    private String weburl;
    /**
     * 种子地址
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
     * 所属地域id
     */
    private Integer areaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname;
    }

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "WebEntity{" +
                "id=" + id +
                ", webname='" + webname + '\'' +
                ", weburl='" + weburl + '\'' +
                ", seedUrls='" + seedUrls + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", contentSelect='" + contentSelect + '\'' +
                ", regex='" + regex + '\'' +
                ", areaId=" + areaId +
                '}';
    }
}
