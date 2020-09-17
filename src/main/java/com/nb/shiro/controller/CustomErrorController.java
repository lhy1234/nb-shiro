package com.nb.shiro.controller;

import com.nb.shiro.enums.ErrorEnum;
import com.nb.shiro.model.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

/**
 * 解决@RestControllerAdvice不能拦截Filter的异常
 * create by lihaoyang on 2020/9/17
 */
@RestController
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Result handleError(HttpServletRequest request)  {
        Object o = request.getAttribute("javax.servlet.error.exception");
        if (o != null) {
            Throwable throwable =  ((Throwable) o).getCause().getCause();
            if(throwable instanceof AuthenticationException){
                AuthenticationException exception = (AuthenticationException)throwable;
                return Result.error(ErrorEnum.SORRY401.getCode(),exception.getMessage());
            }else{
                return Result.error(ErrorEnum.ERROR.getCode(),throwable.getMessage());
            }

        }
        return Result.error500("系统异常");
    }
}
