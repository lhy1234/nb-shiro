package com.nb.shiro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 路径
     */
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    private Integer menuType;
    /**
     * 菜单权限编码
     */
    private String perms;
    /**
     * 菜单排序
     */
    private Double sortNo;
    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    private Integer isLeaf;
    /**
     * 删除状态 0正常 1已删除
     */
    private Integer delFlag;

    @TableField(exist = false)
    private List<SysPermission> children;


}
