package com.nb.shiro.controller;


import com.nb.shiro.model.vo.Result;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys/users")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;



    //@RequiresRoles("")
    @RequiresPermissions("user:mgr")
    @ApiOperation("通过id查询")
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") String id){
        SysUser  user = sysUserService.getById(id);
        if(user == null){
            return Result.error500("未找到对应实体");
        }
        Object o = SecurityUtils.getSubject().getPrincipal();
        System.err.println(o);
        return Result.OK(user);
    }

//    @ApiOperation("addUser")
//    @GetMapping("/addUser")
//    public Result addUser(@RequestBody AddUserForm user){
//        SecurityUtils.getSubject().getPrincipal();
//        sysUserService.addUser(user);
//        return Result.OK(user);
//    }
}

