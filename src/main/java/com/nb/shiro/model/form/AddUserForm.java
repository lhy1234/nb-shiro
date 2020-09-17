package com.nb.shiro.model.form;

import lombok.Data;

import java.util.Date;

/**
 * create by lihaoyang on 2020/9/15
 */
@Data
public class AddUserForm {

    private String id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;



    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 账号状态(1-正常,2-冻结)
     */
    private Integer status;














}
