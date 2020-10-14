package com.nb.shiro.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义参数校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * create by lihaoyang on 2020/9/18
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {
}
