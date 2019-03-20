package com.onevote.command.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        http.antMatcher("/**").csrf().disable()
                .authorizeRequests()
                .antMatchers("/login**", "/swagger-ui**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
