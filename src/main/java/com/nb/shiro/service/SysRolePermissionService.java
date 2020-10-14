package com.nb.shiro.service;

import com.nb.shiro.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 保存角色-权限
     * @param roleId 角色id
     * @param lastPermissionIds 上次的权限 id，多个逗号分隔
     * @param newPermissionIds 新的权限id，多个逗号分隔
     */
    void saveRolePermission(String roleId, String lastPermissionIds, String newPermissionIds);
}
