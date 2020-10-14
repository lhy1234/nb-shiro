package com.nb.shiro.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nb.shiro.entity.SysRole;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.model.vo.Result;
import com.nb.shiro.service.SysRoleService;
import com.nb.shiro.validator.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/sys/roles")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation("角色列表")
    @GetMapping("/list")
    public Result list(String roleName,
                       @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize){

        Result<IPage<SysRole>> result = new Result<>();
        // 条件分页查询
        Page<SysRole> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(roleName)){
            queryWrapper.like("role_name",roleName);
        }
        IPage<SysRole> pageList = sysRoleService.page(page, queryWrapper);
        result.setData(pageList);
        return result;
    }

    @ApiOperation("保存")
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){
        ValidatorUtil.validateEntity(role);
        role.setCreateTime(new Date());
        sysRoleService.save(role);
        return Result.OK();
    }

    @ApiOperation("修改")
    @PostMapping("edit")
    public Result edit(@RequestBody SysRole role){
        ValidatorUtil.validateEntity(role);
        role.setUpdateTime(new Date());
        sysRoleService.updateById(role);
        return Result.OK();
    }

}

