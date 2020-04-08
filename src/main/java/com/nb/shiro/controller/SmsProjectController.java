package com.nb.shiro.controller;


import com.nb.shiro.beans.Result;
import com.nb.shiro.entity.SmsProject;
import com.nb.shiro.service.ISmsProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李浩洋
 * @since 2020-04-08
 */
@RestController
@RequestMapping("/project")
public class SmsProjectController {

    @Autowired
    private ISmsProjectService projectService;

    @RequiresPermissions("project:list")
    @GetMapping("/list")
    public Result list(){
        List<SmsProject> list = projectService.list();
        return Result.ok(list);
    }

    @RequiresPermissions("project:update")
    @GetMapping("/update")
    public Result update(SmsProject project){
        projectService.updateById(project);
        return Result.ok();
    }
}
