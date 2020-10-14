package com.nb.shiro.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-10
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {



    IPage<SysUser> findUserPageListByRoleId(Page<SysUser> page, @Param("roleId") String roleId, @Param("username") String username);
}
