package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：类
 */

public class WebSeedEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5113195625614870736L;

    /**
     * 网站id
     */
    private int webId;
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
     * 类别id
     */
    private String classId;
    /**
     * 与前端对接，以逗号形式拼接成classId
     */
    private int[] classIds;
    /**
     * 类别名称
     */
    private String classNames;

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int[] getClassIds() {
        return classIds;
    }

    public void setClassIds(int[] classIds) {
        this.classIds = classIds;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    @Override
    public String toString() {
        return "WebSeedEntity{" +
                "webId=" + webId +
                ", webName='" + webName + '\'' +
                ", seedurl='" + seedurl + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", contentSelect='" + contentSelect + '\'' +
                ", regex='" + regex + '\'' +
                ", classId='" + classId + '\'' +
                ", classIds=" + Arrays.toString(classIds) +
                ", classNames='" + classNames + '\'' +
                '}';
    }
}
