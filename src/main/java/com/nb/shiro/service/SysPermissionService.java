package com.nb.shiro.service;

import com.nb.shiro.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nb.shiro.model.vo.MenuNode;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 查询菜单列表
     * @param userId
     * @return
     */
//    List<MenuNode> findMenuListByUserId(String userId);

    List<MenuNode> findMenuNodeListByParentId(String parentId);

    //获得用户菜单树
    List<MenuNode> getUserMenuTree(String userId);
}
