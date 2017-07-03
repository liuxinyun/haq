package com.lanwei.haq.bms.entity.dev;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表的详细
 *
 * @author liuxinyun
 * @created 2016/12/22 15:58
 */
public class TableEntity implements Serializable {

    private static final long serialVersionUID = -2355739046297076396L;

    private String name;

    private List<TableFieldEntity> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableFieldEntity> getList() {
        return list;
    }

    public void setList(List<TableFieldEntity> list) {
        this.list = list;
    }

    public TableEntity() {
    }

    public TableEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TableEntity{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
