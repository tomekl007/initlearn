package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    //Field
    @Autowired
    com.stormpath.sdk.application.Application application;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@ModelAttribute String login, @ModelAttribute String password) {
        logger.info("try to log in user : " + login);
        AuthenticationRequest authenticationRequest = new UsernamePasswordRequest(login, password);
        AuthenticationResult result = application.authenticateAccount(authenticationRequest);
        Account account = result.getAccount();
        return new ResponseEntity<>(AccountUtils.mapAccountToUser(account), HttpStatus.OK);
    }
}
