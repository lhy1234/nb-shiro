package com.nb.shiro.model.vo;

import lombok.Data;

/**
 * create by lihaoyang on 2020/9/15
 */
@Data
public class LoginUser {

    /**
     * 登录人id
     */
    private String id;

    /**
     * 登录人账号
     */
    private String username;

    /**
     * 登录人名字
     */
    private String realname;

}
