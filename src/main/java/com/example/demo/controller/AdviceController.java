package com.example.demo.controller;

import com.example.demo.common.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器增强
 * @author Kid
 * @version 1.0
 * @date 2018/9/15
 */
@ControllerAdvice
public class AdviceController {

    private Logger logger=LoggerFactory.getLogger(AdviceController.class);

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Result exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        logger.info("出错了");
        Result result=new Result("无权限");
        result.setCode(Result.CODE_500);
        return result;
    }




}
