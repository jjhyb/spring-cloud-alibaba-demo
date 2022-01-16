package com.yibo.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @Author: huangyibo
 * @Date: 2021/8/28 18:56
 * @Description:
 */
@Configuration
@EnableResourceServer
public class GatewaySecurityConfig extends ResourceServerConfigurerAdapter {

    /**
     * 这里根据业务需求进行灵活配置
     * 如果有一个权限系统，暴露出服务，和网关对接
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //SecurityContextPersistenceFilter是SpringSecurity过滤器链的第一个过滤器，GatewayRateLimitFilter在它之前执行
                //.addFilterBefore(new GatewayRateLimitFilter(), SecurityContextPersistenceFilter.class)
                //表示在ExceptionTranslationFilter过滤器之前执行
                //.addFilterBefore(new GatewayAuditLogFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                //开启/oauth/**验证端口无权限可以访问，即申请令牌的请求不需要带token令牌
                .antMatchers("/token/**").permitAll()
                //其他的请求需要带令牌才可以访问网关
                .anyRequest().authenticated()
                //手动指定访问规则
                .anyRequest().access("#permissionService.hasPermission(request,authentication)");
    }
}
