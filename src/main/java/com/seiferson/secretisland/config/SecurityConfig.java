package com.seiferson.secretisland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .antMatchers("/")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/hPosts")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and().oauth2Login()
                .and().build();
    }

}
