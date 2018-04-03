package com.example.demo.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author：Kid date:2018/3/1
 */
public class MyInterceptor implements HandlerInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
    private static  final  String login="/login";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        Object o1=httpServletRequest.getSession().getAttribute("user");
//        String uri = httpServletRequest.getRequestURI();
//        if(o1==null && !login.equals(uri)){
//            httpServletResponse.sendRedirect(login);
//        }
//
//        String url = httpServletRequest.getRequestURL().toString();
//        String method = httpServletRequest.getMethod();
//
//        String queryString = httpServletRequest.getQueryString();
//        System.out.println(httpServletRequest.getParameterMap());
//        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
//        return true;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
