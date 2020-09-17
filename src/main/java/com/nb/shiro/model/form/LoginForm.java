package com.nb.shiro.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by lihaoyang on 2020/9/15
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginForm {

    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
//    @ApiModelProperty(value = "验证码")
//    private String captcha;
}
