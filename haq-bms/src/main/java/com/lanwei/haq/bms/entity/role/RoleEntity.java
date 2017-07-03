package com.lanwei.haq.bms.entity.role;

import com.lanwei.haq.bms.entity.BaseEntity;
import com.lanwei.haq.comm.enums.RoleTypeEnum;

import java.io.Serializable;

/**
 * 用户权限组实体
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
public class RoleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8457522147993052305L;

    /**
     * 权限组名称
     */
    private String name;

    /**
     * 权限组类型，详见RoleTypeEnum
     */
    private byte type;

    /**
     * 权限组类型，详见GroupTypeEnum
     */
    private String typeStr;

    /**
     * 父id拼接串，用_分开
     */
    private String pidSeries;

    @Override
    public String toString() {
        return "RoleEntity{" +
                "name='" + name + '\'' +
                ", pidSeries='" + pidSeries + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPidSeries() {
        return pidSeries;
    }

    public void setPidSeries(String pidSeries) {
        this.pidSeries = pidSeries;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        RoleTypeEnum typeEnum = RoleTypeEnum.getGroupTypeEnum(type);
        if(null != typeEnum) {
            setTypeStr(typeEnum.getDesc());
        }
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}