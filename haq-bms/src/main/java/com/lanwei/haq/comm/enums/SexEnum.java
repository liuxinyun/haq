package com.lanwei.haq.comm.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxinyun
 * @date 2016/12/19 22:44
 */
public enum SexEnum {

    UNKNOWN((byte) 0, "未知"),

    FEMALE((byte) 2, "女"),

    MAN((byte) 1, "男");

    SexEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private byte code;

    private String desc;

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 传入性别code返回性别枚举
     *
     * @param code 性别码
     * @return SexEnum
     */
    public static SexEnum getSexEnum(byte code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (code == sexEnum.getCode()) {
                return sexEnum;
            }
        }
        return UNKNOWN;
    }

    /**
     * 转换为list类型
     * @return
     */
    public static List<Map<String,Object>> getSex(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (SexEnum sexEnum : SexEnum.values()) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",sexEnum.getCode());
            map.put("desc",sexEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}
