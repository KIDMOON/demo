package com.example.demo.common;

import com.example.demo.DemoApplication;
import org.springframework.boot.SpringApplication;

/**
 * @author：Kid date:2018/3/12
 */
public class Test {

    public static void main(String[] args) {
        Object service = ServiceFactory.getProxyInstance(new MyCglibProxy());

    }
}
