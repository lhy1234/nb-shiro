package com.nb.shiro.service;

import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nb.shiro.model.form.AddUserForm;

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
}
