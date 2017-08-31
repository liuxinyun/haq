package com.lanwei.haq.comm.entity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/8/31 23:09
 * @描述：类
 */

public class UrlDeepth implements Serializable {
    private static final long serialVersionUID = -9133229062170357355L;

    /**
     * 网址
     */
    private String url;
    /**
     * 深度
     */
    private int deepth;

    public UrlDeepth(String url, int deepth) {
        this.url = url;
        this.deepth = deepth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDeepth() {
        return deepth;
    }

    public void setDeepth(int deepth) {
        this.deepth = deepth;
    }

    @Override
    public String toString() {
        return "UrlDeepth{" +
                "url='" + url + '\'' +
                ", deepth=" + deepth +
                '}';
    }
}
