package com.example.demo.controller;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;

public class PointCut extends StaticMethodMatcherPointcutAdvisor {

    public PointCut() {
        setAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("sssss");
                return invocation.proceed();
            }
        });


    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return PatternMatchUtils.simpleMatch("com.example.demo.*",targetClass.getName());
    }


}
