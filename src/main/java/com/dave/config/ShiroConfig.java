package com.dave.config;

import com.dave.service.realm.ShiroUserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Dave
 * @Date: 2019/7/9 20:45
 * @Description: TODO
 */
@Configuration
public class ShiroConfig {
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroUserRealm());
        return securityManager;
    }
    @Bean
    public ShiroUserRealm shiroUserRealm() {
        ShiroUserRealm shiroUserRealm = new ShiroUserRealm();
        shiroUserRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroUserRealm;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        return hashedCredentialsMatcher;
    }
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
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
}