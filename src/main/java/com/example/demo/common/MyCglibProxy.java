package com.example.demo.common;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author：Kid date:2018/3/12
 */
public class MyCglibProxy  implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(method.getName());
        Object  o1=methodProxy.invokeSuper(o,objects);
        return o1;
    }
}
