package com.example.demo.controller;

import com.example.demo.common.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());



    @RequiresPermissions("user:add")
    @GetMapping("info")
    public Result findAdmin(){
        Result result=new Result();
        Subject subject=SecurityUtils.getSubject();
        result.setObject(subject.getPrincipal());
        return result;
    }
}
