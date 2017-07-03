package com.lanwei.haq.bms.entity.role;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * 权限组对应的菜单关系实体
 *
 * @author Sun xushe
 * @date 2016/12/20 8:46
 */
public class RoleMenuRelEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4960412710528336027L;

    /**
     * 权限组id
     */
    private int groupId;

    /**
     * 菜单id
     */
    private int menuId;

    /**
     * 是否默认
     */
    private String isDefault;

    /**
     * 排序码
     */
    private int sort;

    @Override
    public String toString() {
        return "GroupMenuRelEntity{" +
                "groupId=" + groupId +
                ", menuId=" + menuId +
                ", isDefault='" + isDefault + '\'' +
                ", sort=" + sort +
                '}';
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}