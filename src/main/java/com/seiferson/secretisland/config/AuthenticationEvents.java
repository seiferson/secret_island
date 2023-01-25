package com.seiferson.secretisland.config;

import com.seiferson.secretisland.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationEvents {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    @Autowired
    private AccountRepository accountRepo;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.info("Verifying if account is registered internally");
        logger.error("principal" + success.getAuthentication().getPrincipal());
        //accountRepo.existsByUsername(success.getAuthentication().getName())
    }
}
