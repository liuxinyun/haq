package com.lanwei.haq.web.lw.entity.clazz;

/**
 * @作者：刘新运
 * @日期：2017/6/25 16:02
 * @描述：类
 */

public class Clazz {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
