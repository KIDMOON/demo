package com.example.demo.Interceptor;

import com.example.demo.entity.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author：Kid date:2018/3/2
 */
public class LoginFilter extends AccessControlFilter{

    public  static  final  String LOGINOUT="loginOut";
    /**
     * shiro缓存
     */
    private Cache<String, Deque<Serializable>> cache;

    private SessionManager sessionManager;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if (((HttpServletRequest) servletRequest).getRequestURI().equals(getLoginUrl())){
            return  true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        //如果用户没有登录，退出
        if (!subject.isAuthenticated()) {
            redirectToLogin(servletRequest,servletResponse);
            return false;
        }
        Session session = subject.getSession();

        if (session.getAttribute(LOGINOUT)!=null) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(servletRequest);
            //重定向
//            WebUtils.issueRedirect(servletRequest,servletResponse,"/login.ftl");
//            ((HttpServletResponse)servletResponse).sendRedirect("/login.ftl");
            System.out.println("踢出");
            return false;
        }

        Serializable serializable1=session.getId();
        String  name = ((User) subject.getPrincipal()).getName();
        Deque<Serializable> deque=cache.get(name);

        if(deque==null){
            deque=new LinkedList<>();
            cache.put(name,deque);
        }
        if(!deque.contains(serializable1) && session.getAttribute(LOGINOUT)==null){
                deque.push(serializable1);
        }

        Serializable sessionDelete = null;
        while (deque.size()> 1){
            sessionDelete=deque.removeLast();
        }
        try {
            Session session1 = sessionManager.getSession(new DefaultSessionKey(sessionDelete));
            if (session1 != null) {
                session1.setAttribute(LOGINOUT,true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return true;
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
