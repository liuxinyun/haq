package com.lanwei.haq.spider.entity;

import java.util.Date;

/**
 * 公共实体继承类
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
public class BaseEntity {

    /**
     * id
     */
    private int id;

    /**
     * 创建人
     */
    private int creater;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改人
     */
    private int modifier;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除，数据是否逻辑删除F否，T是
     */
    private String isDel;

    /**
     * 当前第几页
     */
    private int pageNum;

    /**
     * 　分页大小
     */
    private int pageSize;

    /**
     * limit开始页
     */
    private int pageStart;

    /**
     * 总页数
     */
    private int total;

    /**
     * 总个数
     */
    private int count;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public int getPageNum() {
        if (pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageStart() {
        pageStart = (getPageNum() - 1) * getPageSize();
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getTotal() {
        total = count / getPageSize() + (count % getPageSize() == 0 ? 0 : 1);
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}