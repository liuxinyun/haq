package com.lanwei.haq.spider.entity.web;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：类
 */

public class WebClassEntity implements Serializable{

    private static final long serialVersionUID = -6960156273140862317L;

    private Integer id;

    /**
     * 网站id
     */
    private Integer webId;
    /**
     * 类别选择器
     */
    private String classSelect;
    /**
     * 原类别名称
     */
    private String sourceName;
    /**
     * 系统对应类别id
     */
    private Integer classId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWebId() {
        return webId;
    }

    public void setWebId(Integer webId) {
        this.webId = webId;
    }

    public String getClassSelect() {
        return classSelect;
    }

    public void setClassSelect(String classSelect) {
        this.classSelect = classSelect;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "WebClassEntity{" +
                "id=" + id +
                ", webId=" + webId +
                ", classSelect='" + classSelect + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", classId=" + classId +
                '}';
    }
}
