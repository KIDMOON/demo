package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author：Kid date:2018/3/4
 */
public interface UserMapper{

     Long insert(User user);

     User findId(Long id);

     void update(@Param("id") Long id,@Param("name") String name);
}
