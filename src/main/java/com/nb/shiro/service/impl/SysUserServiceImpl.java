package com.nb.shiro.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.entity.SysUserDepart;
import com.nb.shiro.entity.SysUserRole;
import com.nb.shiro.mapper.SysRolePermissionMapper;
import com.nb.shiro.mapper.SysUserRoleMapper;
import com.nb.shiro.mapper.SysUserMapper;
import com.nb.shiro.model.request.UserRequest;
import com.nb.shiro.service.SysUserDepartService;
import com.nb.shiro.service.SysUserRoleService;
import com.nb.shiro.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserDepartService sysUserDepartService;

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

    @Override
    public void addUser(UserRequest userRequest) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userRequest,user);

        String salt = RandomStringUtils.random(8,true,true);
        String pwd = DigestUtils.sha1Hex(userRequest.getPassword()+salt);
        user.setPassword(pwd);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        //保存用户
        save(user);
        //用户-角色
        if(!CollectionUtils.isEmpty(userRequest.getRoleIdList())){
            List<SysUserRole> userRoles = new ArrayList<>();
            userRequest.getRoleIdList().forEach(roleId->{
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getId());
                userRoles.add(userRole);
            });
            sysUserRoleService.saveBatch(userRoles);
        }

        //用户-部门
        if(!CollectionUtils.isEmpty(userRequest.getDepartIdList())){
            List<SysUserDepart> userDeparts = new ArrayList<>();
            userRequest.getRoleIdList().forEach(deptId->{
                SysUserDepart userDepart = new SysUserDepart();
                userDepart.setUserId(user.getId());
                userDepart.setDepId(deptId);
                userDeparts.add(userDepart);
            });
            sysUserDepartService.saveBatch(userDeparts);
        }



    }

    @Override
    public IPage<SysUser> findUserPageListByRoleId(Page<SysUser> page, String roleId, String username) {
        return sysUserMapper.findUserPageListByRoleId(page,roleId,username);
    }
}
