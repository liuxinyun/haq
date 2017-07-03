package com.lanwei.haq.comm.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应实体枚举，用于返回响应结果，ajax统一返回码，
 *
 * @author liuxinyun
 * @created 2016/12/22 9:27
 */
public enum ResponseEnum {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 页面没找到
     */
    NOT_FOUND(404, "页面未找到"),

    /**
     * 参数为空
     */
    PARAM_NULL(431, "参数为空"),

    /**
     * 参数不正确
     */
    PARAM_ERROR(432, "提交的参数不正确，请重新填写"),

    /**
     * 默认异常返回
     */
    DEFAULT_ERROR(444, "请求资源失败，请稍后再试"),

    /**
     * 用户未登录
     */
    NO_LOGIN(40013, "您还未登录或者已经登录超时，请重新登录"),

    /**
     * 服务器异常
     */
    SYSTEM_ERROR(500, "服务器异常"),

    /**
     * 服务器繁忙
     */
    SYSTEM_BUSY(50013, "服务器繁忙，请稍后再试"),

    /**
     * 数据库异常
     */
    JDBC_ERROR(506, "数据库发生错误"),

    /**
     * 缓存异常
     */
    CACHE_ERROR(507, "缓存发生异常"),

    /**
     * 接口异常
     */
    INTERFACE_ERROR(42003, "请求接口异常"),

    /**
     * 非运营人员
     */
    NOT_MANAGER(60013, "非运营人员");


    private int code;

    private String msg;

    private Map<String, Object> resultMap;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public Map<String, Object> getResultMap() {
        resultMap = new HashMap<>();
        resultMap.put("code", this.code);
        resultMap.put("msg", this.msg);
        return resultMap;
    }

}
