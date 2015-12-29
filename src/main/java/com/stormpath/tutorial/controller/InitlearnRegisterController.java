package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.tutorial.controller.jsonrequest.AccountData;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tomasz.lelek on 29/12/15.
 */
@Controller
public class InitlearnRegisterController {
    @Autowired
    UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(InitlearnRegisterController.class);
    
    @RequestMapping(value = "/registerAccount", method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity<User> registerNewAccount(@RequestBody AccountData accountData){
        logger.info("aD: " + accountData);
        Account account = userService.createAccount(accountData);
        return new ResponseEntity<>(AccountUtils.mapAccountToUser(account), HttpStatus.OK);
    }
}
