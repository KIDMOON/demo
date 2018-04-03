package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author：Kid date:2018/3/4
 */
public interface UserMapper{

    int insert(User user);
}
