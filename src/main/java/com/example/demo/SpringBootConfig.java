package com.example.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.Interceptor.LoginFilter;
import com.example.demo.Interceptor.MyInterceptor;
import com.example.demo.filter.CrosFilter;
import com.example.demo.shiro.CredentialsMatcher;
import com.example.demo.shiro.MyShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.*;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：Kid date:2018/3/1
 */
@Configuration
public class SpringBootConfig extends WebMvcConfigurerAdapter{

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new MyInterceptor());
    }


    /**
     * 视图解析器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("/login");
    }

    /**
     * 消息内容转换配置
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
    }

    @Bean(name = "ehCacheManager")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager=new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        System.err.println("--------------shiroFilter 已经加载----------------");
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置访问权限
        bean.setLoginUrl("/unlogin");
        bean.setUnauthorizedUrl("/401");
        Map<String,Filter>  filterMap=bean.getFilters();
        filterMap.put("loginFilter",LoginFilter());
        bean.getFilterChainDefinitionMap();
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        //配置不需要验证的权限
        filterChainDefinitionMap.put("/resource/**", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/admin/logout", "anon");
        filterChainDefinitionMap.put("/**","loginFilter");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean
    public CredentialsMatcher createCredentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean(name="myShiroRealm")
    public MyShiroRealm authRealm() {
        MyShiroRealm authRealm=new MyShiroRealm();
        authRealm.setCredentialsMatcher(createCredentialsMatcher());
        return authRealm;
    }


//    @Bean
//    public FilterRegistrationBean crosFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        CrosFilter crosFilter = new CrosFilter();
//        registrationBean.setFilter(crosFilter);
//        List<String> urlPatterns = new ArrayList<>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

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
    @Bean
    public SecurityManager securityManager() {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm());
        manager.setCacheManager(getEhCacheManager());
        manager.setSessionManager(sessionManager());
        return manager;
    }

    /**
     * 配置session管理器
     * @return
     */
    @Bean
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


    public LoginFilter LoginFilter(){
         LoginFilter loginFilter=new  LoginFilter();
         loginFilter.setSessionManager(sessionManager());
         loginFilter.setCache(getEhCacheManager());
         return loginFilter;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
