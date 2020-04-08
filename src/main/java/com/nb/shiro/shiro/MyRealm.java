package com.nb.shiro.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.service.ISysUserService;
import com.nb.shiro.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  自定义Realm实现，该Realm类提供了两个方法：
 *   1，doGetAuthenticationInfo：获取认证信息
 *   2，doGetAuthorizationInfo：获取权限信息
 *  Created by: 李浩洋 on 2020-04-02
 **/
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "userRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Autowired
    private ISysUserService sysUserService;

    //完成身份认证并返回认证信息
    //获取身份验证信息，错误抛出异常
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取token
        String jwtToken = (String) token.getCredentials();
        if(StringUtils.isEmpty(jwtToken)){
            log.info("======== token为空 ==== {}",jwtToken);
            throw new AuthenticationException("token为空");
        }
        //校验token的有效性
        SysUser user = checkToken(jwtToken);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,jwtToken,getName());
        return info;
    }



    private SysUser checkToken(String jwtToken) throws AuthenticationException{
        //解密jwt
        String username = JwtUtil.getUsername(jwtToken);
        if(username == null){
            throw new AuthenticationException("token解析失败");
        }
        SysUser user = sysUserService.findByUsername(username);
        if(user == null){
            throw new AuthenticationException("用户不存在");
        }

//        if(!JwtUtil.verify(jwtToken,username,user.getPassword())){
//            throw new AuthenticationException("token解析失败");
//        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            throw new AuthenticationException("账号已被锁定,请联系管理员!");
        }
        return user;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


}
