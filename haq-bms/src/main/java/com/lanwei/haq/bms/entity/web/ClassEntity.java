package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：系统网站类别实体
 */

public class ClassEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5113195625614870736L;

    /**
     * 类别名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
