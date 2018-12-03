package com.example.demo.shiro;

import com.example.demo.mq.RabbitMqConfig;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author kid
 */
public class Test {

    public static void main(String [] args){
        StaticMethodMatcherPointcut  staticMethodMatcherPointcut=new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return true;
            }
        };
        Advice advice=new SimpleTraceInterceptor();
        Advisor advisor=new DefaultPointcutAdvisor(staticMethodMatcherPointcut,advice);
        ProxyFactory pf1=new ProxyFactory();
        pf1.setTarget(new RabbitMqConfig());
        pf1.addAdvisor(advisor);

        RabbitMqConfig rabbitMqConfig=(RabbitMqConfig)pf1.getProxy();
        rabbitMqConfig.queue();

    }
}
