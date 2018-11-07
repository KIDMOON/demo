package com.example.demo.common;

import com.example.demo.DemoApplication;
import com.example.demo.controller.LoginController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author：Kid date:2018/3/12
 */

public class Test implements Filter {


    private BeanFactory beanFactory;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ssss");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("____________________"+((HttpServletRequest)servletRequest).getRequestURI());

    }

    @Override
    public void destroy() {

    }
}
