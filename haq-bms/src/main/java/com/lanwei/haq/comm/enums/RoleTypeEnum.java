package com.lanwei.haq.comm.enums;

/**
 * 权限组类型
 *
 * @author liuxinyun
 * @date 2017/1/3 11:57
 */
public enum RoleTypeEnum {

    DEV((byte) 1, "开发人员权限组"),
    BMS((byte) 2, "系统管理权限组"),
    USER((byte) 3, "普通用户权限组"),
    INFO((byte) 4, "信息管理权限组")
    ;

    /**
     * 编码
     */
    private byte code;

    /**
     * 描述
     */
    private String desc;

    RoleTypeEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static RoleTypeEnum getGroupTypeEnum(byte code) {
        for (RoleTypeEnum typeEnum : RoleTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum;
            }
        }
        return null;
    }
}
