package com.lanwei.haq.spider.entity.web;

import java.io.Serializable;
import java.util.Date;

/**
 * @作者：刘新运
 * @日期：2017/7/30 21:42
 * @描述：专题类
 */

public class SubjectEntity implements Serializable {
    private static final long serialVersionUID = 2097664023198911605L;

    private Integer id;
    /**
     * 专题名称
     */
    private String name;
    /**
     * 专题关键词，逗号分隔
     */
    private String keywords;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 是否删除：F有效，T已删除
     */
    private String isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keywords='" + keywords + '\'' +
                ", created=" + created +
                ", isDel='" + isDel + '\'' +
                '}';
    }
}
