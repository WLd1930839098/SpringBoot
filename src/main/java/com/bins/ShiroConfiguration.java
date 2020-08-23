package com.bins;

import com.bins.realm.NewsRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    public NewsRealm getRealm() {
        return new NewsRealm();
    }

    @Bean
    public SecurityManager securityManager(NewsRealm newsRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(newsRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置拦截规则
        shiroFilterFactoryBean.setLoginUrl("/admin/toLogin");
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/toLogin", "anon");//不拦截
        filterMap.put("/admin/login", "anon");
        filterMap.put("/admin/**", "authc");//表示拦截/admin路由下的所有请求
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
