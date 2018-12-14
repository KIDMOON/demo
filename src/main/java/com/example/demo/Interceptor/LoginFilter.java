package com.example.demo.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author：Kid date:2018/3/2
 */
@Slf4j
public class LoginFilter extends FormAuthenticationFilter {

    public  static  final  String LOGINOUT="loginOut";
    /**
     * shiro缓存
     */
    private Cache<String, Deque<Serializable>> cache;

    private SessionManager sessionManager;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        String s=((HttpServletRequest) request).getRequestURI();
        if (((HttpServletRequest) request).getRequestURI().equals("/messageHandler")){
            return  true;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

//    @Override
//    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o){
//        if (((HttpServletRequest) servletRequest).getRequestURI().equals(getLoginUrl())){
//            return  true;
//        }
//        return false;
//    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String s=((HttpServletRequest) request).getRequestURI();
        if (((HttpServletRequest) request).getRequestURI().equals("/messageHandler")){
            return  true;
        }
        return super.isAccessAllowed(request,response,mappedValue);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        if (!subject.isAuthenticated()) {
//            redirectToLogin(servletRequest,servletResponse);
            return false;
        }
//        //如果用户没有登录，退出
//        if (!subject.isAuthenticated()) {
//            redirectToLogin(servletRequest,servletResponse);
//            return false;
//        }
//        Session session = subject.getSession();
//
//        if (session.getAttribute(LOGINOUT)!=null) {
//            //会话被踢出了
//            try {
//                //退出登录
//                subject.logout();
//            } catch (Exception e) { //ignore
//            }
//            saveRequest(servletRequest);
//            //重定向
//            WebUtils.issueRedirect(servletRequest,servletResponse,"http:192.169.1.84:8090/admin/unlogin");
//            System.out.println("踢出");
//            return false;
//        }
//
//        Serializable serializable1=session.getId();
//        String  name = ((User) subject.getPrincipal()).getName();
//        Deque<Serializable> deque=cache.get(name);
//
//        if(deque==null){
//            deque=new LinkedList<>();
//            cache.put(name,deque);
//        }
//        if(!deque.contains(serializable1) && session.getAttribute(LOGINOUT)==null){
//                deque.push(serializable1);
//        }
//
//        Serializable sessionDelete = null;
//        while (deque.size()> 1){
//            sessionDelete=deque.removeLast();
//        }
//        try {
//            Session session1 = sessionManager.getSession(new DefaultSessionKey(sessionDelete));
//            if (session1 != null) {
//                session1.setAttribute(LOGINOUT,true);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
//        servletResponse
        if (isLoginRequest(servletRequest, servletResponse)) {
            return true;
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            //参考原来FormAuthenticationFilter的写法，将Redirect去掉，修改成返回json
            //saveRequestAndRedirectToLogin(request, response);
            //saveRequest(request);  去掉此方法，避免在登录失败时也向前端发送set-cookie命令
            //返回json
            return true;
        }
    }

    public Cache<String, Deque<Serializable>> getCache() {
        return cache;
    }

    public void setCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-session");
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }




}
