package com.lanwei.haq.spider.entity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/27 23:04
 * @描述：类
 */

public class NewsEntity implements Serializable{

    private static final long serialVersionUID = -5266964755198076104L;

    /**
     * 网站站点
     */
    private String website;
    /**
     * 新闻地址
     */
    private String url;
    /**
     * 新闻标题
     */
    private String title;
    /**
     * 新闻内容
     */
    private String content;
    /**
     * 新闻时间
     */
    private String datetime;
    /**
     * 图片地址
     */
    private String img_path;
    /**
     * 所属专题id用空格拼接
     */
    private String subject;
    /**
     * 系统分类的id用空格拼接
     */
    private String classify;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "website='" + website + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", datetime='" + datetime + '\'' +
                ", img_path='" + img_path + '\'' +
                ", subject='" + subject + '\'' +
                ", classify='" + classify + '\'' +
                '}';
    }
}
