package com.stormpath.tutorial.config;

import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.stormpath.sdk.servlet.authc.FailedAuthenticationRequestEvent;
import com.stormpath.sdk.servlet.event.RequestEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tomasz.lelek on 18/12/15.
 */
public class LoginEventListener extends RequestEventListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginEventListener.class);
    @Override
    public void on(FailedAuthenticationRequestEvent e) {
        logger.info("---> " + e.getException());
    }

    @Override
    public void on(RegisteredAccountRequestEvent e){
        logger.info("--> " + e.getResponse().getStatus());
        
    }
}
