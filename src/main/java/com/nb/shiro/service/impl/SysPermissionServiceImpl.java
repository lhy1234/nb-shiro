package com.nb.shiro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nb.shiro.entity.SysPermission;
import com.nb.shiro.mapper.SysPermissionMapper;
import com.nb.shiro.model.vo.MenuNode;
import com.nb.shiro.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;









    @Override
    public List<MenuNode> getUserMenuTree(String userId) {
        //1,获取用户的第一级菜单
        List<MenuNode> topList = sysPermissionMapper.findUserTopMenuNodeList(userId);
        if(topList == null || topList.isEmpty()){
            return Collections.emptyList();
        }
        //递归查询子节点
        List<MenuNode> treeList = getMeAndChildren(topList,null);
        return treeList!=null?treeList:Collections.emptyList();
    }

    private List<MenuNode> getMeAndChildren(List<MenuNode> menuList,String parentId) {
        menuList.forEach(item->{
            if(item.getIsLeaf() == 0){
                List<MenuNode> children = findMenuNodeListByParentId(item.getId());
                item.setChildren(children);
                //递归
                getMeAndChildren(children,item.getId());
            }
        });
        return menuList;
    }




    @Override
    public List<MenuNode> findMenuNodeListByParentId(String parentId) {
        return sysPermissionMapper.findMenuNodeListByParentId(parentId);
    }
}
