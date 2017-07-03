package com.lanwei.haq.comm.exception;

/**
 * 未登录异常
 *
 * @author liuxinyun
 * @date 2017/1/6 16:13
 */
public class NotLoginException extends BaseException {

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
