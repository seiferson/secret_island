package com.seiferson.secretisland.config;

import com.seiferson.secretisland.model.Account;
import com.seiferson.secretisland.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationEvents {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    @Autowired
    private AccountRepository accountRepo;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        logger.info("Verifying if account is registered internally");
        DefaultOAuth2User user = (DefaultOAuth2User) event.getAuthentication().getPrincipal();
        String username = user.getAttribute("login");
        logger.error("Username: " + username);

        if(!accountRepo.existsByUsername(username)) {
            Account accnt = new Account();
            accnt.setStatus("ACTIVE");
            accnt.setUsername(username);
            accnt.setJoined(new Date());

            accountRepo.insert(accnt);
        }
    }
}
