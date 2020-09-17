package com.nb.shiro.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb.shiro.constant.SysConstant;
import com.nb.shiro.entity.SysUser;
import com.nb.shiro.exception.BizException;
import com.nb.shiro.service.SysUserService;
import com.nb.shiro.utils.JwtUtil;
import com.nb.shiro.utils.RedisUtil;
import com.nb.shiro.model.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *  自定义Realm实现，该Realm类提供了两个方法：
 *   1，doGetAuthenticationInfo：获取认证信息
 *   2，doGetAuthorizationInfo：获取权限信息
 *  Created by: 李浩洋 on 2020-04-02
 **/
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getName() {
        return "userRealm";
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Autowired
    private SysUserService sysUserService;

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
        LoginUser loginUser = checkToken(jwtToken);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser,jwtToken,getName());
        return info;
    }


    /**
     * 检验token得有效性
     * @param jwtToken
     * @return
     * @throws AuthenticationException
     */
    private LoginUser checkToken(String jwtToken) throws AuthenticationException{
        //解密jwt
        String username = JwtUtil.getUsername(jwtToken);
        if(username == null){
            throw new AuthenticationException("token解析失败");
        }
        //TODO:缓存取
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username",username));

        if(user == null){
            throw new AuthenticationException("用户不存在");
        }


        if(!JwtUtil.verify(jwtToken,username)){
            throw new AuthenticationException("token解析失败");
        }
        // 判断用户状态
        if (user.getStatus()==0) {
            throw new AuthenticationException("账号已被锁定,请联系管理员!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(jwtToken, username)) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user,loginUser);
        return loginUser;
    }

    /**
     * 权限信息认证(包括角色以及权限)是用户访问controller的时候才进行验证(redis存储的此处权限信息)
     * 触发检测用户权限时才会调用此方法，例如checkRole,checkPermission
     *
     * @param principals 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============Shiro权限认证开始============ [ roles、permissions]==========");
        //得到用户
        LoginUser loginUser = (LoginUser)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = sysUserService.findUserRolesSet(loginUser.getId());
		authorizationInfo.setRoles(roleSet);

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = sysUserService.getUserPermissionsSet(loginUser.getId());
        authorizationInfo.addStringPermissions(permissionSet);
        log.info("===============Shiro权限认证成功==============");
        return authorizationInfo;
    }


    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     *       用户过期时间 = Jwt有效时间 * 2。
     *
     * @param userName
     * @return
     */
    public boolean jwtTokenRefresh(String token, String userName) {
        String cacheToken = String.valueOf(redisUtil.get(SysConstant.PREFIX_USER_TOKEN + token));
        if (StringUtils.isNotEmpty(cacheToken)) {
            //校验token有效性
            if (!JwtUtil.verify(cacheToken, userName)) {
                String newAuthorization = JwtUtil.sign(userName);
                // 设置超时时间
                redisUtil.set(SysConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(SysConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME *2 / 1000);
                log.info("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— "+ token);
            }
            return true;
        }
        return false;
    }


}
