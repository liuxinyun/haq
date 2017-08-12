package com.lanwei.haq.comm.entity;

/**
 * @作者：刘新运
 * @日期：2017/8/12 22:51
 * @描述：类
 */

public class ImgPath {

    /**
     * 原地址
     */
    private String source;
    /**
     * 本地地址
     */
    private String local;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "ImgPath{" +
                "source='" + source + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
