package com.nb.shiro.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 替换shiro原生的token，原生token ： UsernamePasswordToken 记录着username，password，rememberMe 信息
 * 而jwt只要一个jwt就行了
 * Created by: 李浩洋 on 2020-04-03
 **/
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
