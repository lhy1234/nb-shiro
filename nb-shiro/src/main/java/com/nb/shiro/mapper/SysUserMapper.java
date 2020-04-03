package com.nb.shiro.mapper;

import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 李浩洋
 * @since 2020-04-02
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    SysUser findByUsername(@Param("username") String username);
}
