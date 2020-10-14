package com.nb.shiro.model.resp;

import com.nb.shiro.entity.SysUser;
import lombok.Data;

/**
 * create by lihaoyang on 2020/10/10
 */
@Data
public class UserInfo extends SysUser {

    private String token;
}
