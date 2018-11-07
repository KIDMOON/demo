package com.example.demo.mapper;

import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author：Kid date:2018/3/4
 */
public interface UserMapper{

     Long insert(User user);

     @Cached(name="UserMapper.", key="#id", expire = 3600)
     User findId(Long id);

     @CacheUpdate(name="userCache.", key="#user.userId", value="#user")
     void update(@Param("id") Long id,@Param("name") String name);

     @Cached(name="userCache.", key="#name", expire = 3600)
     User findOneName(@Param("name") String name);
}
