package com.seiferson.secretisland.model;

import org.springframework.security.core.GrantedAuthority;

public class AppAuthority implements GrantedAuthority {

    private String authority;

    public AppAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
