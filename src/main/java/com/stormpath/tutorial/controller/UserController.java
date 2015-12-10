package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private Client client;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
     public List<Account> getAllUsers(){
        List<Account> list = new ArrayList<>();
        client.getAccounts().iterator().forEachRemaining(list::add);
        return list;
    }
}
