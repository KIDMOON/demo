package com.example.demo.shiro;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author：Kid date:2018/3/1
 */
public class MyShiroRealm extends AuthorizingRealm{


    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        String name=usernamePasswordToken.getUsername();
         User user=userMapper.findOneName(name);
         if(user==null){
             throw  new UnknownAccountException();
         }

        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(name,user.getPassword(),getName());
        return simpleAuthenticationInfo;
    }
}
