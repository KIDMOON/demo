package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 控制器增强
 * @author Kid
 * @version 1.0
 * @date 2018/9/15
 */
@ControllerAdvice
public class AdviceController {

    private Logger logger=LoggerFactory.getLogger(AdviceController.class);

//    @ExceptionHandler(Exception.class)
//    public Result exceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception ex){
//        logger.info("出错了");
//        Result result=new Result(ex.getMessage());
//        result.setCode(Result.CODE_500);
//        return result;
//    }




}
