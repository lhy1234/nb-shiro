package com.nb.shiro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nb.shiro.entity.SysRolePermission;
import com.nb.shiro.mapper.SysRolePermissionMapper;
import com.nb.shiro.service.SysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Transactional
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {


    @Override
    public void saveRolePermission(String roleId, String lastPermissionIds, String newPermissionIds) {

        List<String> lastPermissionIdArray = Lists.newArrayList(StringUtils.splitByWholeSeparator(lastPermissionIds,","));

        List<String> newPermissionIdsArray = Lists.newArrayList(StringUtils.splitByWholeSeparator(newPermissionIds,","));

        //交集不动，old➖new --> 要删除；new➖old -->新增
        List<String> deletePermissionIdList = ListUtils.subtract(lastPermissionIdArray,newPermissionIdsArray);
        List<String> addPermissionIdList = ListUtils.subtract(newPermissionIdsArray,lastPermissionIdArray);


        deletePermissionIdList.forEach(permissionId->{
            remove(new QueryWrapper<SysRolePermission>().eq("role_id",roleId).eq("permission_id",permissionId));
        });

        List<SysRolePermission> addEntityList  = new ArrayList<>();
        addPermissionIdList.forEach(permissionId->{
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            addEntityList.add(rolePermission);
        });
        saveBatch(addEntityList);
    }

    public static void main(String[] args) {

        List<String> lastPermissionIdArray = Lists.newArrayList("1","2","3");

        List<String> newPermissionIdsArray = Lists.newArrayList();

        //交集不动，old➖new --> 要删除；new➖old -->新增
        List<Integer> deletePermissionIdList = ListUtils.subtract(lastPermissionIdArray,newPermissionIdsArray);
        List<Integer> addPermissionIdList = ListUtils.subtract(newPermissionIdsArray,lastPermissionIdArray);

        System.err.println(JSONObject.toJSONString(deletePermissionIdList));

        System.err.println(JSONObject.toJSONString(addPermissionIdList));


    }
}
