package com.nb.shiro.controller;


import com.nb.shiro.beans.Result;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 李浩洋
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/users")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable int id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

}
