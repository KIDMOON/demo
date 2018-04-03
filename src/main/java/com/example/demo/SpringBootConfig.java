package com.example.demo;

import com.example.demo.Interceptor.LoginFilter;
import com.example.demo.Interceptor.MyInterceptor;
import com.example.demo.shiro.MyShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.*;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author：Kid date:2018/3/1
 */
@Configuration
public class SpringBootConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new MyInterceptor());
    }


    @Bean(name = "ehCacheManager")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager=new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }


    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager,@Qualifier("loginFilter") LoginFilter loginFilter) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login.ftl");
        bean.setSuccessUrl("/home");
        //配置访问权限
        Map<String,Filter>  filterMap=new LinkedHashMap<>(1);
        filterMap.put("loginFilter",loginFilter);
        bean.setFilters(filterMap);

        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/test","loginFilter");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher createCredentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean(name="myShiroRealm")
    public MyShiroRealm authRealm() {
        MyShiroRealm authRealm=new MyShiroRealm();
        authRealm.setCredentialsMatcher(createCredentialsMatcher());
        return authRealm;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }


    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager() {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm());
        manager.setCacheManager(getEhCacheManager());
        manager.setSessionManager(sessionManager());
        return manager;
    }

    //配置session管理器
    @Bean(name="sessionManager")
    public SessionManager sessionManager() {
        System.err.println("--------------配置session管理器 已经加载----------------");
        DefaultWebSessionManager manager=new DefaultWebSessionManager();
        manager.setGlobalSessionTimeout(3600000);
        manager.setCacheManager(getEhCacheManager());
        manager.setSessionDAO(enterpriseCacheSessionDAO());
        manager.setDeleteInvalidSessions(true);
        manager.setSessionValidationSchedulerEnabled(true);
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionIdCookie(simpleCookie());
        return manager;
    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie=new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    //session 缓存
    @Bean
    public EnterpriseCacheSessionDAO  enterpriseCacheSessionDAO(){
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO=new EnterpriseCacheSessionDAO();
        //session缓存在事务中
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator());
        return enterpriseCacheSessionDAO;

    }

    @Bean
    public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean(name ="loginFilter")
    public LoginFilter LoginFilter(){
         LoginFilter loginFilter=new  LoginFilter();
         loginFilter.setSessionManager(sessionManager());
         loginFilter.setCache(getEhCacheManager());
        return loginFilter;
    }

}
