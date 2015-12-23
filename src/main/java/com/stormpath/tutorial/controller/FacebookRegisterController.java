package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.oauth.*;
import com.stormpath.sdk.provider.FacebookProviderData;
import com.stormpath.sdk.provider.ProviderAccountRequest;
import com.stormpath.sdk.provider.ProviderAccountResult;
import com.stormpath.sdk.provider.Providers;

import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
@Controller
public class FacebookRegisterController {
    @Autowired
    Application application;

    private static final Logger logger = LoggerFactory.getLogger(FacebookRegisterController.class);

    @RequestMapping(value = "/registerFacebookAccount/{accessToken}", method = RequestMethod.POST)
    ResponseEntity<String> registerFacebookAccount(@PathVariable("accessToken") String accessToken) {

        ProviderAccountRequest request = Providers.FACEBOOK.account()
                .setAccessToken(accessToken)
                .build();

        ProviderAccountResult result = application.getAccount(request);
        Account account = result.getAccount();
        logger.info("account for register fb : "+ AccountUtils.mapAccountToUser(account));
        FacebookProviderData providerData = (FacebookProviderData) account.getProviderData();
        logger.info("token for that account" + providerData.getAccessToken());

        PasswordGrantRequest passwordGrantRequest = Oauth2Requests.PASSWORD_GRANT_REQUEST.builder()
          .setLogin(account.getEmail())
          .build();
        
        OauthGrantAuthenticationResult oauthGrantAuthenticationResult = Authenticators.PASSWORD_GRANT_AUTHENTICATOR
          .forApplication(application)
          .authenticate(passwordGrantRequest);
        AccessToken accessToken1 = oauthGrantAuthenticationResult.getAccessToken();
        logger.info("oauth access token : " + accessToken1);

        return new ResponseEntity<>(providerData.getAccessToken(), HttpStatus.OK);
    }
}
