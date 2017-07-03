package com.lanwei.haq.comm.exception;

/**
 * 项目统一异常，自定义异常需要继承该异常
 *
 * @author liuxinyun
 * @date 2017/1/5 14:47
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
