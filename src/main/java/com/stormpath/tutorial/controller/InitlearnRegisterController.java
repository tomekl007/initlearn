package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.resource.ResourceException;
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
    ResponseEntity<User> registerNewAccount(@RequestBody AccountData accountData) {
        logger.info("aD: " + accountData);
        try {
            Account account = userService.createAccount(accountData);
            return new ResponseEntity<>(AccountUtils.mapAccountToUser(account), HttpStatus.OK);
        } catch (ResourceException re) {
            if (accountAlreadyExists(re)) {
                logger.warn("account " + accountData.email + " already exists");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (passwordHasNotEnoughtCharacters(re)) {
                logger.warn("password with length " + accountData.password.length() + " has not enough characters");
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            } else if(re.getCode() == 2006) {
                logger.warn("Account email address is in an invalid format");
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }else {
                logger.error("unexpected error occurred", re);
                throw re;
            }
        }
    }

    private boolean passwordHasNotEnoughtCharacters(ResourceException re) {
        return re.getCode() == 2007;
    }

    private boolean accountAlreadyExists(ResourceException re) {
        return re.getCode() == 2001;
    }
}
