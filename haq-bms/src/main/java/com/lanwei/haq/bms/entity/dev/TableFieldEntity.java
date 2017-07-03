package com.lanwei.haq.bms.entity.dev;

import java.io.Serializable;

/**
 * table实体类
 *
 * @author liuxinyun
 * @created 2016/12/22 17:26
 */
public class TableFieldEntity  implements Serializable {

    private static final long serialVersionUID = -284282463694453878L;
    /**
     * 属性名称
     */
    private String field;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 可以为null
     */
    private String isNull;

    /**
     * 主键标示
     */
    private String key;

    /**
     * 默认值
     */
    private String DEFAULT;

    /**
     * 扩展
     */
    private String extra;

    @Override
    public String toString() {
        return "TableEntity{" +
                "field='" + field + '\'' +
                ", type='" + type + '\'' +
                ", NULL='" + isNull + '\'' +
                ", key='" + key + '\'' +
                ", DEFAULT='" + DEFAULT + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDEFAULT() {
        return DEFAULT;
    }

    public void setDEFAULT(String DEFAULT) {
        this.DEFAULT = DEFAULT;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }
}
