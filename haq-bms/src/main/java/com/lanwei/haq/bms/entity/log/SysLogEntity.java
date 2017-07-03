package com.lanwei.haq.bms.entity.log;

import com.lanwei.haq.bms.entity.BaseEntity;
import com.lanwei.haq.comm.util.DateUtil;
import com.lanwei.haq.comm.util.EnumDateCode;

import java.io.Serializable;

/**
 * @作者: liuxinyun
 * @日期: 2017/6/15 11:48
 * @描述:
 */
public class SysLogEntity extends BaseEntity implements Serializable{

    /**
     * 描述
     */
    private String description;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 请求ip
     */
    private String ip;
    /**
     * 创建时间格式化
     */
    private String createdStr;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreatedStr() {
        if(super.getCreated() != null){
            return DateUtil.formatDate(EnumDateCode.YEAR_MM_DD_HH_MM_SS, super.getCreated());
        }else{
            return "";
        }
    }

    public void setCreatedStr(String createdStr) {
        this.createdStr = createdStr;
    }

    @Override
    public String toString() {
        return "SysLogEntity{" +
                "description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
