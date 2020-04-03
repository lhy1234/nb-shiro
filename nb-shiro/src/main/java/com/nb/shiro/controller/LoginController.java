package com.nb.shiro.controller;

import com.nb.shiro.beans.Result;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.service.ISysUserService;
import com.nb.shiro.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: 李浩洋 on 2020-04-02
 **/
@Slf4j
@RequestMapping("/")
@RestController
public class LoginController {


    @Autowired
    private ISysUserService userService;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestParam String username, @RequestParam String password){

        SysUser user = userService.findByUsername(username);
        if(user == null){
            return Result.error(101,"用户名或密码错误");
        }
        if(!StringUtils.equals(user.getPassword(),password)){
            return Result.error(101,"用户名或密码错误");
        }
        String token = JwtUtil.sign(username,password);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("user",user);
        resultMap.put("token",token);
        return Result.ok(resultMap);
    }
}
