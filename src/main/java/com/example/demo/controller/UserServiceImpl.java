package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author：Kid date:2018/3/4
 */
@Service
public class UserServiceImpl implements  UserSerivce{

    @Autowired
    private   UserMapper userMapper;

    /**
     * spring 事务测试
     * 需要指定事务回滚的异常
     * private方法无法 事务
     * @param user
     * @throws Exception
     */
    @Override
    public void addUser(User user) throws Exception {
        userMapper.insert(user);
    }
}
