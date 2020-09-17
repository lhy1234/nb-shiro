package com.nb.shiro.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create by lihaoyang on 2020/9/15
 */
@Data
public class MenuNode {


    private String id;
    private String name;
    private String url; //路径
    private String component;//组件
//    private String path;
//    private String redirect;//一级菜单跳转地址
    private int isRoute;
    private int isLeaf;

    private List<MenuNode> children = new ArrayList<>();

}
