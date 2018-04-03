package com.example.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author：Kid date:2018/3/1
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        String name=usernamePasswordToken.getUsername();

        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(name,"111",getName());
        return simpleAuthenticationInfo;
    }
}
