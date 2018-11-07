package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author：Kid date:2018/3/4
 */
@Service
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private   UserMapper userMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private UserRep userRep;

    /**
     * spring 事务测试
     * 需要指定事务回滚的异常
     * private方法无法 事务
     * @param user
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addUser(User user) throws IllegalAccessException {
//        userRep.save(user);
        userMapper.insert(user);
//        throw new IllegalAccessException();
//        testService.ss(user.getId());
    }

}
