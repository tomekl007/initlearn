package com.stormpath.tutorial.user;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tomasz.lelek on 16/12/15.
 */
@Component
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Client client;


    public  List<User> findUsersByEmail(String email) {
        List<Account> accounts = findAccountsByEmail(email);
        return AccountUtils.mapToUsers(accounts);
    }

    public List<Account> findAccountsByEmail(String email) {
        List<Account> accounts = new ArrayList<>();
        client.getAccounts(Collections.singletonMap("email", email))
                .iterator()
                .forEachRemaining(accounts::add);
        return accounts;
    }
}
