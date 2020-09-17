package com.nb.shiro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb.shiro.model.vo.Result;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.model.form.LoginForm;
import com.nb.shiro.service.SysUserService;
import com.nb.shiro.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 * create by lihaoyang on 2020/9/15
 */
@Api(tags="用户登录")
@RequestMapping("sys")
@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginForm loginForm){

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username",username));
        if(sysUser == null){
            return Result.error500("用户名或密码错误");
        }
        if(!StringUtils.equals(sysUser.getPassword(),password)){
            return Result.error500("用户名或密码错误");
        }
        String jwt = JwtUtil.sign(username);
        return Result.OK(jwt);

    }
}
