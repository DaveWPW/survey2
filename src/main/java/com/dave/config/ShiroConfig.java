package com.dave.config;

import com.dave.service.realm.ShiroUserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Dave
 * @Date: 2019/7/9 20:45
 * @Description: TODO
 */
@Configuration
public class ShiroConfig {
    /**
     * 安全管理器；Shiro的核心
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroUserRealm());
        return securityManager;
    }
    /**
     * 自定义Realm（可以多个）
     * @return
     */
    @Bean
    public ShiroUserRealm shiroUserRealm() {
        ShiroUserRealm shiroUserRealm = new ShiroUserRealm();
        shiroUserRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroUserRealm;
    }
    /**
     * 配置加密匹配，使用MD5的方式
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        return hashedCredentialsMatcher;
    }
    /**
     * 配置拦截器
     * 1. 定义拦截URL权限，优先级从上到下
     *      1). anon  : 匿名访问，无需登录
     *      2). authc : 登录后才能访问
     *      3). logout: 登出
     *      4). roles : 角色过滤器
     * 2. URL 匹配风格
     *      1). ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
     *      2). *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1；
     *      2). **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //添加Shiro内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<String,String>();
        filterMap.put("/bower_components/**", "anon");
        filterMap.put("/dist/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/doSurveyUI.do", "anon");
        filterMap.put("/survey/*.do", "anon");
        filterMap.put("/doLogin", "anon");
        filterMap.put("/doLogout", "logout");
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
        return shiroFilterFactoryBean;
    }
    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /**
     * 开启Shiro的注解（配置授权属性）
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorization = new AuthorizationAttributeSourceAdvisor();
        authorization.setSecurityManager(securityManager());
        return authorization;
    }
}