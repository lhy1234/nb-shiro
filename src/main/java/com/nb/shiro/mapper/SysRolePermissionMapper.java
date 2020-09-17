package com.nb.shiro.mapper;

import com.nb.shiro.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Repository
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    List<String> getUserPermissionsByUserId(@Param("userId") String userId);
}
