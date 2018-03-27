package com.lanwei.haq.comm.entity;

/**
 * @作者：刘新运
 * @日期：2017/8/12 22:51
 * @描述：类
 */

public class ImgPath {

    /**
     * 图片名称
     */
    private String name;
    /**
     * 原地址
     */
    private String source;
    /**
     * 本地地址
     */
    private String local;

    /**
     * json转化使用，开发人员禁止使用
     */
    public ImgPath() {
    }

    public ImgPath(String name, String source, String local) {
        this.name = name;
        this.source = source;
        this.local = local;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
