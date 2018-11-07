package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author：Kid date:2018/3/1
 */
@Controller
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

    @RequestMapping(value = "/login",method= RequestMethod.POST)
    public String loginUser(String userName, String passWord,HttpSession httpSession) throws IOException {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(userName,passWord);
        Subject subject=SecurityUtils.getSubject();
       try {
           subject.login(usernamePasswordToken);
           Object user= subject.getPrincipal();
           httpSession.setAttribute("user", user);
           return  "index";
       }catch (Exception e){
       }
        return  "redirect:/login";
    }


    @RequestMapping(value = "/home",method= RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/test",method= RequestMethod.POST)
    public void loginUser() throws Exception {
        stringRedisTemplate.opsForValue().set("test","11");
        User user = new User();
        user.setName("bak");
        user.setPassword("11111");
        userSerivce.addUser(user);
    }


}
