package com.example.demo.common;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author：Kid date:2018/3/12
 */
public class ServiceFactory {
    public static Object getProxyInstance(MethodInterceptor methodInterceptor){
        Enhancer enhancer = new Enhancer();
        // 将Enhancer中的superclass属性赋值成BookServiceBean
        enhancer.setSuperclass(Object.class);
        // 将Enhancer中的callbacks属性赋值成myProxy
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();

    };


}
