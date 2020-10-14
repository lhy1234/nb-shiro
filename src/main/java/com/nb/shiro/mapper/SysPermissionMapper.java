package com.nb.shiro.mapper;

import com.nb.shiro.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nb.shiro.model.vo.MenuNode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    List<SysPermission> findMenuListByUserId(@Param("userId") String userId);

    List<String> findAllMenuIdListByUserId(@Param("userId") String userId);

    List<SysPermission> findListByParentId(@Param("parentId") String parentId);

    List<MenuNode> findUserTopMenuNodeList(@Param("userId") String userId);

    List<MenuNode> findMenuNodeListByParentId(@Param("parentId") String parentId);

    /**
     * 查询用户的shiro权限字符串
     * @param userId
     * @return
     */
    List<String> getUserShiroPermissions(@Param("userId") String userId);

    @Select("select id,parent_id parentId, `name` ,path,component,is_leaf from sys_permission order by sort_no")
    List<MenuNode> getAllMenuNodeList();
}
