package com.nb.shiro.constant;

/**
 * create by lihaoyang on 2020/9/15
 */
public class SysConstant {

    public static final String AUTH_TOKEN = "Authorization";


    /** 登录用户Token令牌缓存KEY前缀 */
    public static final String PREFIX_USER_TOKEN  = "prefix_user_token_";
    /** Token缓存时间：3600秒即一小时 */
    public static final int  TOKEN_EXPIRE_TIME  = 3600;


    /*****菜单类型(0:一级菜单; 1:子菜单:2:按钮权限) ******/
    public static final int  MENU_TYPE_0 = 0;//
    public static final int  MENU_TYPE_1 = 1;
    public static final int  MENU_TYPE_2 = 2;


}
