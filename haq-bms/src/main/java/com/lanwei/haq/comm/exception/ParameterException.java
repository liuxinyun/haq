package com.lanwei.haq.comm.exception;

/**
 * 参数不正确的异常
 * 在用户输入参数不正确的时候抛出这个异常，返回的msg会提示给用户
 *
 * @author liuxinyun
 * @date 2016/12/29 18:12
 */
public class ParameterException extends BaseException {

    public ParameterException(String msg) {
        super(msg);
    }

    public ParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
