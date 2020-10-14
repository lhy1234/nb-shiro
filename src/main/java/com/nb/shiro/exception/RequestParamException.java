package com.nb.shiro.exception;


/**
 * 自定义 请求参数 异常
 * create by lihaoyang on 2020/9/18
 */
public class RequestParamException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private int code = 500;

    public RequestParamException(String message) {
        super(message);
        this.message = message;
    }

    public RequestParamException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
