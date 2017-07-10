package com.lanwei.haq.comm.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxinyun
 * @date 2016/12/19 22:44
 */
public enum SpeedEnum {

    LOW((byte) 5, "低速"),

    MID((byte) 10, "中速"),

    HIGH((byte) 20, "高速");

    SpeedEnum(byte code, String desc) {
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
     * 传入code返回枚举
     *
     * @param code
     * @return
     */
    public static SpeedEnum getEnum(byte code) {
        for (SpeedEnum sexEnum : SpeedEnum.values()) {
            if (code == sexEnum.getCode()) {
                return sexEnum;
            }
        }
        return MID;
    }

    /**
     * 转换为list类型
     * @return
     */
    public static List<Map<String,Object>> getList(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (SpeedEnum speedEnum : SpeedEnum.values()) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",speedEnum.getCode());
            map.put("desc",speedEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}
