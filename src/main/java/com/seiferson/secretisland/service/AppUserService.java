package com.seiferson.secretisland.service;

import com.seiferson.secretisland.model.Account;
import com.seiferson.secretisland.model.AppAuthority;
import com.seiferson.secretisland.model.AppOAuthUser;
import com.seiferson.secretisland.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AppUserService extends DefaultOAuth2UserService {

    @Autowired
    private AccountRepository accountRepo;

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        String username = user.getAttribute("login");
        Account accnt = null;

        logger.info("Processing authentication for user " + username);

        if(!accountRepo.existsByUsername(username)) {
            logger.info("New app user, registering at internal DB");
            ArrayList<String> authorities = new ArrayList<>();
            authorities.add("SI_AUTH_USER");

            accnt = new Account();
            accnt.setUsername(username);
            accnt.setJoined(new Date());
            accnt.setAuthorities(authorities);

            accountRepo.insert(accnt);
        } else {
            accnt = accountRepo.findByUsername(username);
        }

        AppOAuthUser appUser = new AppOAuthUser(user);
        ArrayList<AppAuthority> appAuthorities = new ArrayList<>();
        for(String auth : accnt.getAuthorities()) {
            appAuthorities.add(new AppAuthority(auth));
        }
        appUser.setAuthorities(appAuthorities);

        return appUser;
    }

}
