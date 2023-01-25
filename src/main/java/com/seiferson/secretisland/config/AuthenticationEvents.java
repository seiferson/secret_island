package com.seiferson.secretisland.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationEvents {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.error("Authentication at " + new Date());
    }
}
