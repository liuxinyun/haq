package com.lanwei.haq.bms.entity.web;

import java.io.Serializable;

/**
 * @description：日期
 * @author：liuxinyun
 * @date：2018/1/15 21:55
 */
public class DayEntity implements Serializable {
    private static final long serialVersionUID = 5821464487908390058L;

    private int num;

    private String date;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DayEntity{" +
                "num=" + num +
                ", date='" + date + '\'' +
                '}';
    }
}
