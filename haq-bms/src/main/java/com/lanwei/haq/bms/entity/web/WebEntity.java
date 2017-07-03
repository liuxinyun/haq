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

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
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

    @Override
    public String toString() {
        return "WebEntity{" +
                "webname='" + webname + '\'' +
                ", weburl='" + weburl + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", contentSelect='" + contentSelect + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
