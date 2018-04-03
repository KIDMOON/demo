package com.example.demo.controller;

import com.example.demo.component.Sender;
import com.example.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author：Kid date:2018/3/1
 */
@Controller
public class LoginController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private Sender helloSender;

    @RequestMapping(value = "/login",method= RequestMethod.GET)
    public String login() {
        return "resource/login";
    }

    @RequestMapping(value = "/loginss",method= RequestMethod.POST)
    public String loginUser(String userName, String passWord,HttpSession httpSession) {
        helloSender.send();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(userName,passWord);
        Subject subject=SecurityUtils.getSubject();
       try {
           subject.login(usernamePasswordToken);
           Object user= subject.getPrincipal();
           httpSession.setAttribute("user", user);
           return "index";
       }catch (Exception e){
           return "login";
       }
    }

    @RequestMapping(value = "/test",method= RequestMethod.POST)
    public String loginUser() throws Exception {
        User user = new User();
        user.setName("bak");
        user.setPassword("11111");
        userSerivce.addUser(user);
        return "test";

    }


}
