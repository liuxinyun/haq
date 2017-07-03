package com.lanwei.haq.bms.entity.menu;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * 菜单实体类
 *
 * @author liuxinyun
 * @date 2016/12/20 8:10
 */
public class MenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8836551777065693462L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型，参见MenuTypeEnum
     */
    private byte type;

    private String typeStr;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 项id
     */
    private int menuId;

    /**
     * 项名称
     */
    private String menuName;

    /**
     * 模块id
     */
    private int moduleId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 权限id
     */
    private int roleId;

    /**
     * 排序码
     */
    private int sort;

    /**
     * 是否为末节点
     */
    private String isLast;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", typeStr='" + typeStr + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", roleId=" + roleId +
                ", sort=" + sort +
                ", isLast='" + isLast + '\'' +
                '}';
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public MenuEntity() {
    }

    public MenuEntity(byte type) {
        this.type = type;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }
}
