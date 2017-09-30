package com.lanwei.haq.comm.entity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/8/31 23:09
 * @描述：类
 */

public class UrlDepth implements Serializable {
    private static final long serialVersionUID = -9133229062170357355L;

    /**
     * 网址
     */
    private String url;
    /**
     * 深度
     */
    private int depth;

    public UrlDepth(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "UrlDepth{" +
                "url='" + url + '\'' +
                ", depth=" + depth +
                '}';
    }
}
