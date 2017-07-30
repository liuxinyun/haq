package com.lanwei.haq.spider.entity.web;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/7/30 21:37
 * @描述：类
 */

public class WebConfigEntity implements Serializable {

    private static final long serialVersionUID = -2491556878707283567L;

    private Integer id;
    /**
     * 类别：1间隔，2线程数
     */
    private Integer type;
    /**
     * 根据类别分别表示：间隔大小（分钟）/线程数量（个）
     */
    private Integer data;
    /**
     * 状态：1正在用，0未使用
     */
    private Integer status;
    /**
     * 是否删除：F有效，T已删除
     */
    private String isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WebConfigEntity{" +
                "id=" + id +
                ", type=" + type +
                ", data=" + data +
                ", status=" + status +
                ", isDel='" + isDel + '\'' +
                '}';
    }
}
