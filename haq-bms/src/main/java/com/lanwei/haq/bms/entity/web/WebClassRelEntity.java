package com.lanwei.haq.bms.entity.web;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;

/**
 * @作者：刘新运
 * @日期：2017/6/15 21:00
 * @描述：网站原类别和系统对应类别关系
 */

public class WebClassRelEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5113195625614870736L;

    /**
     * 网站id
     */
    private int webId;
    /**
     * 网站名称
     */
    private String webname;

    /**
     * 网站原类别名称
     */
    private String sourceName;
    /**
     * 标题选择器
     */
    private String titleSelect;
    /**
     * 系统类别id
     */
    private int classId;
    /**
     * 系统类别名称
     */
    private String className;

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitleSelect() {
        return titleSelect;
    }

    public void setTitleSelect(String titleSelect) {
        this.titleSelect = titleSelect;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "WebClassRelEntity{" +
                "webId=" + webId +
                ", webname='" + webname + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", titleSelect='" + titleSelect + '\'' +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                '}';
    }
}
