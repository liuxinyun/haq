package com.lanwei.haq.bms.entity.notice;

import com.lanwei.haq.bms.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告实体类
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
public class NoticeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5482832601017844405L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 可见权限组，多个权限组用英文逗号分开
     */
    private String groupList;

    /**
     * 状态，详见NoticeStatusEnum
     */
    private byte status;

    /**
     * 排序码
     */
    private int sort;

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", groupList='" + groupList + '\'' +
                ", status=" + status +
                ", sort=" + sort +
                '}';
    }

    public NoticeEntity() {
    }

    public NoticeEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        this.groupList = groupList;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}