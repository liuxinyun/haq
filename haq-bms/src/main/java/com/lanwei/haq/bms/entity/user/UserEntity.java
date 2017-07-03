package com.lanwei.haq.bms.entity.user;

import com.lanwei.haq.bms.entity.BaseEntity;
import com.lanwei.haq.comm.enums.SexEnum;
import com.lanwei.haq.comm.util.StringUtils;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5847943969526631525L;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别，参见枚举SexEnum
     */
    private byte sex;

    /**
     * 性别描述
     */
    private String sexStr;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 所属机构名称
     */
    private String branchName;

    /**
     * 权限组id
     */
    private int roleId;

    /**
     * 权限类型
     */
    private byte roleType;

    /**
     * 用户类型
     */
    private String userType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSexStr() {
        if (StringUtils.isBlank(sexStr)) {
            return SexEnum.getSexEnum(sex).getDesc();
        }
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sexStr +
                ", password='" + password + '\'' +
                ", headImage='" + headImage + '\'' +
                ", branchName='" + branchName + '\'' +
                ", roleId=" + roleId +
                ", userType='" + userType + '\'' +
                '}';
    }

    public byte getRoleType() {
        return roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }
}
