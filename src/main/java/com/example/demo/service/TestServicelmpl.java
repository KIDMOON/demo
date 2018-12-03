package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServicelmpl implements TestService {

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    @Qualifier("transactionManager")
//    private PlatformTransactionManager platformTransactionManager;


    @Override
    @Transactional(value="transactionManager",readOnly=true)
    public void ss(Long userId) {
        User user=userMapper.findId(userId);
//        userMapper.update(user.getId(),"sssssss");
        System.out.println(userId);
    }
}
