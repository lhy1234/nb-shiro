package com.nb.shiro.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * create by lihaoyang on 2020/9/15
 */

@Data
public class UserRequest {

    private String id;

    /**
     * 登录账号
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 工号，唯一键
     */

    private String workNo;

    /**
     * 职务，关联职务表
     */
    private String post;

    //角色id列表
    private List<String> roleIdList;
    //部门idlist
    private List<String> departIdList;

    /**
     * 身份（1普通成员 2上级）
     */
    private Boolean userIdentity;

    /**
     * 负责部门
     */
    private String departIds;

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
     * 座机号
     */
    private String telephone;

}
