package com.nb.shiro.enums;

public enum ErrorEnum {


    SUCCESS(200,"操作成功"),
    ERROR(500,"操作失败"),
    SORRY401(401,"认证失败"),
    SORRY403(403,"无权限"),


    LOGIN_ERROR(1001,"用户名或密码错误"),
    ;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;



    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
