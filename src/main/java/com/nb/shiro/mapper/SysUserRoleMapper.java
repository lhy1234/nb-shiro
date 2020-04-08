package com.nb.shiro.mapper;

import com.nb.shiro.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-08
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {



    List<String> findUserRolesByUserId(@Param("userId") int userId);
}
