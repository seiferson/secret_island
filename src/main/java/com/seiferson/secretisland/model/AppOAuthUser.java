package com.seiferson.secretisland.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AppOAuthUser implements OAuth2User {

    private OAuth2User ogOAuthUser;
    private List<AppAuthority> authorities;

    public AppOAuthUser(OAuth2User ogOAuthUser) {
        this.ogOAuthUser = ogOAuthUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return ogOAuthUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<AppAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return ogOAuthUser.getName();
    }
}
