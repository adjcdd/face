package com.grgbanking.framework.bd.face.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by wyf on 2017/7/5.
 */
@Configuration
//@EnableWebSecurity: 禁用Boot的默认Security配置，配合@Configuration启用自定义配置
// （需要扩展WebSecurityConfigurerAdapter）
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，
// 例如最常用的@PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    //configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
    //定义URL路径应该受到保护，哪些不应该
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 例如以下代码指定了/和/index不需要任何认证就可以访问，其他的路径都必须通过身份验证。
                .antMatchers("/**/*", "/*").permitAll()
                .anyRequest().authenticated();
        //关闭csrf 防止循环定向
        http.csrf().disable();
    }

}
