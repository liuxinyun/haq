package com.lanwei.haq.comm.enums;

/**
 * @作者：刘新运
 * @日期：2017/10/7 23:33
 * @描述：枚举
 */

public enum ConfigEnum {
    CRON(1, "时间间隔"),
    THREAD_NUM(2, "线程数"),
    ;

    private int type;

    private String msg;

    ConfigEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
