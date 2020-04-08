package com.nb.shiro.service;

import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 李浩洋
 * @since 2020-04-02
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);

    /**
     * 查询用户的角色集合
     * @param userId
     * @return
     */
    Set<String> findUserRolesSet(int userId);


    /**
     * 查询用户的权限集合
     * @param userId
     * @return
     */
    Set<String> findUserPermissionsSet(int userId);
}
