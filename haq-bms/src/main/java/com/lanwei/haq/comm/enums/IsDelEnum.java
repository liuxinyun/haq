package com.lanwei.haq.comm.enums;

/**
 * 是否删除枚举
 *
 * @author liuxinyun
 * @date 2016/12/20 8:46
 */
public enum IsDelEnum {

    T("T", "是"),
    F("F", "否");

    IsDelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * T或者F
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public IsDelEnum getIsDelEnum(String isDel) {
        if (T.getCode().equals(isDel)) {
            return T;
        }
        return F;
    }
}
