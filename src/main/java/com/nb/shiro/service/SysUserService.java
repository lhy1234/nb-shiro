package com.nb.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nb.shiro.model.request.UserRequest;

import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
public interface SysUserService extends IService<SysUser> {


    Set<String> findUserRolesSet(String userId);

    Set<String> getUserPermissionsSet(String userId);



    void addUser(UserRequest userRequest);

    /**
     * 分页查询角色下的用户列表
     * @param page
     * @param roleId
     * @param username
     * @return
     */
    IPage<SysUser> findUserPageListByRoleId(Page<SysUser> page, String roleId, String username);
}
