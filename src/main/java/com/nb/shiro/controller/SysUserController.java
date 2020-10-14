package com.nb.shiro.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nb.shiro.model.request.UserRequest;
import com.nb.shiro.model.vo.Result;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.service.SysUserService;
import com.nb.shiro.validator.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @ApiOperation("添加用户")
    @PostMapping("add")
    public Result addUser(@RequestBody UserRequest userRequest) {

        ValidatorUtil.validateEntity(userRequest);
        //用户名重复校验
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", userRequest.getUsername()).eq("del_flag", 0));
        if (user != null) {
            return Result.error500("用户名已存在");
        }
        //邮箱重复校验
        user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("email", userRequest.getEmail()).eq("del_flag", 0));
        if (user != null) {
            return Result.error500("邮箱已存在");
        }
        sysUserService.addUser(userRequest);
        return Result.OK();
    }


    @ApiOperation("用户列表")
    @GetMapping("/list")
    public Result queryPageList(SysUser user,
                                                @RequestParam(name = "pageNo", defaultValue = "1",required = false) Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10",required = false) Integer pageSize) {
        Result<IPage<SysUser>> result = new Result<>();
        // 条件分页查询
        Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(user.getUsername())) {
            queryWrapper.eq("username", user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getRealname())) {
            queryWrapper.eq("realname", user.getRealname());
        }
        IPage<SysUser> pageList = sysUserService.page(page, queryWrapper);
        //批量查询用户的所属部门
        //step.1 先拿到全部的 useids
        //step.2 通过 useids，一次性查询用户的所属部门名字
//        List<String> userIds = pageList.getRecords().stream().map(SysUser::getId).collect(Collectors.toList());
//        if(userIds!=null && userIds.size()>0){
//            Map<String,String> useDepNames = sysUserService.getDepNamesByUserIds(userIds);
//            pageList.getRecords().forEach(item->{
//                item.setOrgCodeTxt(useDepNames.get(item.getId()));
//            });
//        }
//        result.setSuccess(true);
//        result.setResult(pageList);
        result.setData(pageList);
        //return result;
        return Result.OK(pageList);
    }


    //@RequiresRoles("")
    @RequiresPermissions("user:mgr")
    @ApiOperation("通过id查询")
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") String id) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            return Result.error500("未找到对应实体");
        }
        Object o = SecurityUtils.getSubject().getPrincipal();
        System.err.println(o);
        return Result.OK(user);
    }


//    @GetMapping("/list")
//    public Result list(String realName){
//        QueryWrapper queryWrapper = new QueryWrapper<>().eq("realname",realName);
//        List<SysUser> list = sysUserService.list(queryWrapper);
//        return Result.OK(list);
//    }


    @ApiOperation("某个角色下的用户列表")
    @GetMapping("/roleUserList")
    public Result<IPage<SysUser>> roleUserList(@RequestParam String roleId,
                                               String username,
                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<SysUser>> result = new Result<IPage<SysUser>>();
        Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
        IPage<SysUser> pageList = sysUserService.findUserPageListByRoleId(page, roleId, username);
        result.setData(pageList);
        return result;
    }




}
