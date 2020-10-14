package com.nb.shiro.exception;

import com.nb.shiro.model.vo.Result;
import com.nb.shiro.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RequestParamException.class)
    public Result handleRRException(RequestParamException e){
        log.warn("参数校验异常:"+e);
        return Result.error(e.getCode(),e.getMessage());
    }


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e){
        log.error(e.getMessage(), e);
        return Result.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<?> handleAuthenticationException(AuthenticationException e){
        log.error(e.getMessage(), e);
        return Result.error(401,e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result<?> handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        return Result.error(ErrorEnum.SORRY403);
    }



    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e){
        log.error(e.getMessage(), e);
        return Result.error500(e.getMessage());
    }


}
