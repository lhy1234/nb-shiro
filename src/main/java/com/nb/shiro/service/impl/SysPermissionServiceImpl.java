package com.nb.shiro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nb.shiro.constant.SysConstant;
import com.nb.shiro.entity.SysPermission;
import com.nb.shiro.mapper.SysPermissionMapper;
import com.nb.shiro.model.vo.MenuNode;
import com.nb.shiro.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author
 * @since 2020-09-10
 */
@Transactional
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public List<MenuNode> getUserMenuTree(String userId) {//1,获取用户的第一级菜单
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

    @Override
    public Set<String> getUserShiroPermissions(String userId) {
        List<String> permissions = new ArrayList<>();
        //这里需要判断一下是不是超级管理员，是就查询所有
        if(StringUtils.equals(userId,"xxoo")){
            //findAll

        }else{
            permissions = sysPermissionMapper.getUserShiroPermissions(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();

        if(!permissions.isEmpty()){
            //一个权限，shiro的perms可能是多个逗号分隔的，比如
            //查看用户，perms == sys:user:list,sys:user:info  需要分隔处理一下
            permissions.forEach(perms ->{
                if(StringUtils.isNotBlank(perms)){
                    permsSet.addAll(Lists.newArrayList(StringUtils.split(perms,",")));
                }
            });
        }
        return permsSet;
    }

    @Override
    public List<MenuNode> getPermissionTree() {

        //查询所有
        List<MenuNode> allNodeList = sysPermissionMapper.getAllMenuNodeList();
        //根节点
        List<MenuNode> rootList =  allNodeList.stream().filter(item->StringUtils.isEmpty(item.getParentId())).collect(Collectors.toList());
        for(MenuNode root:rootList){
            buildChildren(allNodeList,root);
        }
        return rootList;
    }

    private void buildChildren(List<MenuNode> list,MenuNode parentNode){

        List<MenuNode> childrenList = new ArrayList<>();
        for (MenuNode node : list) {
            if (StringUtils.equals(node.getParentId(),parentNode.getId())) {
                if(node.getIsLeaf() == 0){
                    buildChildren(list,node);
                }
                childrenList.add(node);//是这个node的子节点
                parentNode.setChildren(childrenList);
            }
        }
    }

    @Override
    public void addPermission(SysPermission sysPermission) {
        //如果是一级菜单，parentId设置为空
        if(sysPermission.getMenuType()== SysConstant.MENU_TYPE_0){
            sysPermission.setParentId(null);
        }
        //如果parentId不为空，说明是二级菜单或者按钮，设置父级不是叶子节点
        if(StringUtils.isNotBlank(sysPermission.getParentId())){
            setIsLeaf(sysPermission.getParentId(),0);
        }

        sysPermission.setIsLeaf(1);
        sysPermission.setDelFlag(0);
        save(sysPermission);
    }


    @Override
    public void setIsLeaf(String id, int isLeaf) {
        SysPermission update = new SysPermission();
        update.setId(id);
        update.setIsLeaf(isLeaf);
        updateById(update);
    }
}
