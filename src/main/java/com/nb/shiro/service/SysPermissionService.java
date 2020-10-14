package com.nb.shiro.service;

import com.nb.shiro.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nb.shiro.model.vo.MenuNode;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取用户的shiro权限字符串集合
     * @param userId 用户id
     * @return
     */
    Set<String> getUserShiroPermissions(String userId);

    /**
     * 权限树
     * @return
     */
    List<MenuNode> getPermissionTree();


    /**
     * 添加菜单
     * @param sysPermission
     */
    void addPermission(SysPermission sysPermission);

    /**
     * 设置是否是叶子节点
     * @param id
     * @param isLeaf  0-否 1-是
     */
    void setIsLeaf(String id,int isLeaf);
}
