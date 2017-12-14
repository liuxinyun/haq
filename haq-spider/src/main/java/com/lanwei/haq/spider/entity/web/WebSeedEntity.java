package com.lanwei.haq.spider.entity.web;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：类
 */

public class WebSeedEntity implements Serializable{

    private static final long serialVersionUID = -6960156273140862317L;

    private Integer id;
    /**
     * 网站id
     */
    private Integer webId;
    /**
     * 网站名称
     */
    private String webName;
    /**
     * 种子地址
     */
    private String seedurl;
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
     * 所属类别id
     */
    private Integer classId;
    /**
     * 所属地域
     */
    private Integer areaId;
    /**
     * 所属类别名称
     */
    private Integer className;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWebId() {
        return webId;
    }

    public void setWebId(Integer webId) {
        this.webId = webId;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getSeedurl() {
        return seedurl;
    }

    public void setSeedurl(String seedurl) {
        this.seedurl = seedurl;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getClassName() {
        return className;
    }

    public void setClassName(Integer className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "WebSeedEntity{" +
                "id=" + id +
                ", webId=" + webId +
                ", webName='" + webName + '\'' +
                ", seedurl='" + seedurl + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", contentSelect='" + contentSelect + '\'' +
                ", regex='" + regex + '\'' +
                ", classId=" + classId +
                ", areaId=" + areaId +
                ", className=" + className +
                '}';
    }
}
