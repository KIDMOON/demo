package com.example.demo.shiro;

import com.example.demo.entity.Permission;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Kid date:2018/3/1
 */
public class MyShiroRealm extends AuthorizingRealm{


    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权信息 权限
        User sysUser =  (User)principals.getPrimaryPrincipal();
        //TODO 数据库查询权限 ，此处暂时没有查询数据库
        List<Permission> sysPermissions = new ArrayList<>();
        List<String> permissionValus = new ArrayList<>();
        if (sysPermissions != null) {
            System.out.println(sysPermissions.size());
            for (Permission sysPermission : sysPermissions) {
                permissionValus.add(sysPermission.getPermission());
                System.out.println(sysPermission.toString());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo
                = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissionValus);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        String name=usernamePasswordToken.getUsername();
        User user=userMapper.findOneName(name);
         if(user==null){
             throw  new UnknownAccountException();
         }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        return simpleAuthenticationInfo;
    }
}
