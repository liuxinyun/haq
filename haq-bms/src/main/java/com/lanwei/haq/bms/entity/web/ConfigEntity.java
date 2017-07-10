package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.comm.enums.SpeedEnum;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/7/9 21:48
 * @描述：类
 */

public class ConfigEntity implements Serializable {

    private static final long serialVersionUID = -619683019307647333L;

    private int id;

    /**
     * 类别：1表示间隔，2表示线程数
     */
    private byte type;

    /**
     * 数据：当类别为间隔时，表示分钟，当类别为线程数时，表示线程数
     */
    private int data;

    /**
     * 描述：线程数才会有该项，分别对应低中高三个速度
     */
    private String desc;

    /**
     * 状态：1表示正在用，0表示没有用
     */
    private byte status;

    private String isDel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getDesc() {
        if (this.type == 2){
            return SpeedEnum.getEnum((byte)this.data).getDesc();
        }
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

}
