package com.lanwei.haq.comm.enums;

/**
 * 菜单类型枚举
 *
 * @author liuxinyun
 * @created 2016/12/26 11:05
 */
public enum MenuEnum {

    MODULE((byte) 1, "模块"),
    MENU((byte) 2, "菜单"),
    ITEM((byte) 3, "菜单项");

    private byte code;

    private String desc;

    MenuEnum(byte code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "MenuEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static MenuEnum getMenuEnum(byte code) {
        for (MenuEnum menuType : MenuEnum.values()) {
            if (code == menuType.getCode()) {
                return menuType;
            }
        }
        return null;
    }
}
