package com.challenge.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity //Simple WebSecurityConfiguration for the purpose of the exercise.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/products/**", "/swagger**")
            .permitAll().anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and().csrf().disable();
    }
}