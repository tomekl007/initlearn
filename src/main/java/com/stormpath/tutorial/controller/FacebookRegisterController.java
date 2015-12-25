package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directories;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.provider.FacebookProviderData;
import com.stormpath.sdk.provider.ProviderAccountRequest;
import com.stormpath.sdk.provider.ProviderAccountResult;
import com.stormpath.sdk.provider.Providers;
import com.stormpath.tutorial.controller.jsonrequest.AccountData;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
@Controller
public class FacebookRegisterController {
    @Autowired
    Application application;
    
    @Autowired
    Client client;

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
        return new ResponseEntity<>(providerData.getAccessToken(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/registerAccount", method = RequestMethod.POST)
    ResponseEntity<User> registerNewAccount(@ModelAttribute AccountData accountData){
        Account account = client.instantiate(Account.class)
                .setUsername(accountData.email)
                .setEmail(accountData.email)
                .setGivenName(accountData.givenName)
                .setMiddleName("")
                .setSurname(accountData.surname)
                .setPassword(accountData.password)
                .setStatus(AccountStatus.ENABLED);


        String href = "https://api.stormpath.com/v1/directories/1Ly7Mnnag7uuPlSzTyCzPA";
        Directory directory = client.getDataStore().getResource(href, Directory.class);
        directory.createAccount(account);
        account.save();
        return new ResponseEntity<>(AccountUtils.mapAccountToUser(account), HttpStatus.OK);
    }
}
