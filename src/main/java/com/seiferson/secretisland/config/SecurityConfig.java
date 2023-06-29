package com.seiferson.secretisland.config;

import com.seiferson.secretisland.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Autowired
    private AppUserService userService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .anyRequest()
                    .permitAll()
                .and()
                    .csrf()
                        .disable()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(userService)
                        .and()
                .and().build();
    }

}
