package com.nb.shiro.validator;



import com.nb.shiro.exception.RequestParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * create by lihaoyang on 2020/9/18
 */
public class ValidatorUtil {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RequestParamException  校验不通过，则报RequestParamException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RequestParamException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new RequestParamException(constraint.getMessage());
        }
    }
}
