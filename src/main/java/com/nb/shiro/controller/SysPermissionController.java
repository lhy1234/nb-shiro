package com.nb.shiro.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb.shiro.entity.SysPermission;
import com.nb.shiro.model.vo.MenuNode;
import com.nb.shiro.model.vo.Result;
import com.nb.shiro.service.SysPermissionService;
import com.nb.shiro.model.vo.LoginUser;
import com.nb.shiro.service.SysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private SysRolePermissionService sysRolePermissionService;



    //@RequiresRoles({"admin"})
    @ApiOperation("新增菜单")
    @PostMapping("/add")
    public Result add(@RequestBody SysPermission sysPermission){
        //参数校验，一级菜单，path、component

        sysPermissionService.addPermission(sysPermission);

        return Result.OK();
    }


    @ApiOperation("用户权限")
    @GetMapping("getUserPermissions")
    public Result getUserPermissions(){
        LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        //查询用户的菜单
        List<MenuNode> menuList = sysPermissionService.getUserMenuTree(loginUser.getId());
        //查询用户的shiro权限
        Set<String> permissions = sysPermissionService.getUserShiroPermissions(loginUser.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("menuList",menuList);
        map.put("permissions",permissions);
        return Result.OK(map);
    }


    /**
     * 所有的权限树
     * @return
     */
    @ApiOperation("权限树")
    @GetMapping("permissionTree")
    public Result permissionTree(){
        //查询所有的菜单
        List<MenuNode> list = sysPermissionService.getPermissionTree();
        List<MenuNode> treeList = new ArrayList<>();
        return Result.OK(list);
    }


    @ApiOperation("保存角色的权限")
    @PostMapping("/saveRolePermission")
    public Result saveRolePermission(@RequestBody JSONObject jsonObject){
        String roleId = jsonObject.getString("roleId");
        String newPermissionIds = jsonObject.getString("newPermissionIds");
        String lastPermissionIds = jsonObject.getString("lastPermissionIds");
        sysRolePermissionService.saveRolePermission(roleId,lastPermissionIds,newPermissionIds);
        return Result.OK();
    }





}

