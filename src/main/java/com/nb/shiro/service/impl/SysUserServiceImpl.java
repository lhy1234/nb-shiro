package com.nb.shiro.service.impl;

import com.nb.shiro.entity.SysUser;
import com.nb.shiro.entity.SysUserRole;
import com.nb.shiro.mapper.SysRolePermissionMapper;
import com.nb.shiro.mapper.SysUserRoleMapper;
import com.nb.shiro.model.form.AddUserForm;
import com.nb.shiro.mapper.SysUserMapper;
import com.nb.shiro.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public Set<String> findUserRolesSet(String userId) {
        List<String> roles = sysUserRoleMapper.findRolesByUserId(userId);
        log.info("-------通过数据库读取用户拥有的角色,userId： {},Roles size:{}------", userId ,  roles!=null?roles.size():0);
        return new HashSet<>(roles);
    }

    @Override
    public Set<String> getUserPermissionsSet(String userId) {
        List<String> permissions = sysRolePermissionMapper.getUserPermissionsByUserId(userId);
        log.info("-------通过数据库读取用户拥有的权限userId：{},,Perms size:{}------ "+ userId, (permissions==null?0:permissions.size()) );
        return new HashSet<>(permissions);
    }
}
