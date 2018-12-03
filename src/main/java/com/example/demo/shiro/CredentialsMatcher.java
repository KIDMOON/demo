package com.example.demo.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author：Kid date:2018/3/1
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher{


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
           UsernamePasswordToken token1= (UsernamePasswordToken) token;
           String passWord=new String(token1.getPassword());
            //数据库中的密码，传入比对的密码
           String passWord2= (String) info.getCredentials();
           return this.equals(passWord,passWord2);
    }
}
