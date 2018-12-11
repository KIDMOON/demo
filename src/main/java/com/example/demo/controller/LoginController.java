package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.common.TestName;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserSerivce;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author：Kid date:2018/3/1
 */
@RestController
public class LoginController {

    private Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;
//    @Autowired
//    private Sender helloSender;

    @RequestMapping(value = "/login",method= RequestMethod.GET)
    public String login() { return "resource/login";
    }

    @RequestMapping(value = "/admin/login",method= RequestMethod.POST)
    @ResponseBody
    public Result loginUser(@RequestBody UserDTO userDTO, HttpSession httpSession) throws IOException {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(userDTO.getUserName(),userDTO.getPassWord());
        Subject subject=SecurityUtils.getSubject();
        Result result=new Result();
       try {
           subject.login(usernamePasswordToken);
           Object user= subject.getPrincipal();
           httpSession.setAttribute("user", user);
           return  result;
       }catch (Exception e){
           result.setMessage("账号密码不匹配");
           result.setCode(Result.CODE_500);
           return result;
       }
    }

    @RequestMapping(value = "/home",method= RequestMethod.GET)
    public String home() {
        return "index";
    }

//    @RequestMapping(value = "/test",method= RequestMethod.POST)
//    public void loginUser() throws Exception {
//        stringRedisTemplate.opsForValue().set("test","11");
//        User user = new User();
//        user.setName("bak");
//        user.setPassword("11111");
//        userSerivce.addUser(user);
//    }
    @RequestMapping(value = "/test",method= RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/admin/logout",method= RequestMethod.GET)
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        subject.logout();
        return result;

    }

    @RequestMapping(value = "/admin/unlogin",method= RequestMethod.GET)
    public Result unlogin() {
        Result result = new Result();
        result.setMessage("not  login");
        result.setCode(301);
        return result;
    }



    @RequestMapping(value = "/401",method= RequestMethod.GET)
    public Result permission() {
        Result result = new Result();
        result.setMessage("not permission");
        result.setCode(302);
        return result;
    }
}
