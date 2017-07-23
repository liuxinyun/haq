package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：系统地区实体
 */

public class AreaEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5113195625614870736L;

    /**
     * 地域名称
     */
    private String name;
    /**
     * 父id，后期扩展多级地域使用。
     */
    private int parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "AreaEntity{" +
                "name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
