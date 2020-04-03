package com.nb.shiro.service;

import com.nb.shiro.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 李浩洋
 * @since 2020-04-02
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);
}
