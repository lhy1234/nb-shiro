package com.nb.shiro.controller;


import com.nb.shiro.model.vo.MenuNode;
import com.nb.shiro.model.vo.Result;
import com.nb.shiro.service.SysPermissionService;
import com.nb.shiro.model.vo.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;


    @ApiOperation("用户权限")
    @GetMapping("getUserPermissions")
    public Result getUserPermissions(){
        LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        List<MenuNode> menuList2 = sysPermissionService.getUserMenuTree(loginUser.getId());
        return Result.OK(menuList2);
    }

}

