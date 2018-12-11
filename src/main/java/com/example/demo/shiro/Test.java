package com.example.demo.shiro;

import com.example.demo.mq.RabbitMqConfig;
import org.aopalliance.aop.Advice;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kid
 */
public class Test {

    private String ss="ssss";

    private void ss(){
        System.out.println("sssss");
    }

    public static void main(String [] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        StaticMethodMatcherPointcut  staticMethodMatcherPointcut=new StaticMethodMatcherPointcut() {
//            @Override
//            public boolean matches(Method method, Class<?> targetClass) {
//                return true;
//            }
//        };
//        Advice advice=new SimpleTraceInterceptor();
//        Advisor advisor=new DefaultPointcutAdvisor(staticMethodMatcherPointcut,advice);
//        ProxyFactory pf1=new ProxyFactory();
//        pf1.setTarget(new RabbitMqConfig());
//        pf1.addAdvisor(advisor);
//
//        RabbitMqConfig rabbitMqConfig=(RabbitMqConfig)pf1.getProxy();
//        rabbitMqConfig.queue();


        Method field = Test.class.getDeclaredMethod("ss");
        field.setAccessible(true);
        field.invoke(new Test());
    }
}
