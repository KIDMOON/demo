package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class description goes here.
 *
 * @author Kid
 * @version 1.0
 * @date 2018/9/17
 */
public interface UserRep extends JpaRepository<User,Long> {
}
