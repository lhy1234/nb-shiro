package com.nb.shiro.controller;


import com.nb.shiro.beans.Result;
import com.nb.shiro.beans.VO.AreaIndexVO;
import com.nb.shiro.beans.VO.AreaPinYinVO;
import com.nb.shiro.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/sysArea")
public class SysAreaController {

    @Autowired
    private SysAreaService areaService;


    @GetMapping("/indexList")
    public Result indexList(){
        List<AreaPinYinVO> resultList = areaService.findIndexList();
        return Result.ok(resultList);
    }

}

