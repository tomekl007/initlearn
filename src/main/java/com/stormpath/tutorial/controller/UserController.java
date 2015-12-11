package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.group.Group;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);
    public static final String SCREEN_HERO_FIELD = "screenHero";

    @Autowired
    private Client client;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<Account> list = new ArrayList<>();
        client.getAccounts().iterator().forEachRemaining(list::add);
        List<User> users = mapToUsers(list);
        logger.info("Return users : " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private List<User> mapToUsers(List<Account> list) {
        return list
                .stream()
                .map(a ->
                        mapAccountToUser(a)).collect(Collectors.toList());
    }

    private User mapAccountToUser(Account a) {
        return new User(a.getEmail(), a.getFullName(), a.getGivenName(), a.getMiddleName(),
                AccountUtils.getCustomFieldValue(a, SCREEN_HERO_FIELD));
    }

    private List<User> mapToUsers(AccountList list) {
        List<Account> accounts = new ArrayList<>();
        list
                .iterator()
                .forEachRemaining(accounts::add);

        return mapToUsers(accounts);
    }

    @RequestMapping(value = "users/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable("email") String email) {
        logger.info("find by email : " + email);
        List<User> users = findUsersByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private List<User> findUsersByEmail(String email) {
        List<Account> accounts = findAccountsByEmail(email);
        return mapToUsers(accounts);
    }

    private List<Account> findAccountsByEmail(String email) {
        List<Account> accounts = new ArrayList<>();
        client.getAccounts(Collections.singletonMap("email", email))
                .iterator()
                .forEachRemaining(accounts::add);
        logger.info("accounts : " + accounts);
        return accounts;
    }

    @RequestMapping(value = "group/users/{groupName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getTeachers(@PathVariable("groupName") String groupName) {

        Optional<AccountList> accountForGroup = StreamSupport.stream(Spliterators
                .spliteratorUnknownSize(client.getGroups(Collections.singletonMap("name", groupName))
                        .iterator(), Spliterator.ORDERED), false)
                .findFirst()
                .map(Group::getAccounts);

        if (!accountForGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapToUsers(accountForGroup.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/screenhero", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addScreenHeroToUser(@RequestBody String sc, @PathVariable("email") String email) {
        List<Account> accountsByEmail = findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addCustomFieldToAccount(a, SCREEN_HERO_FIELD, sc));
        return new ResponseEntity<>(mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping("/me")
    ResponseEntity<User> me(Account account) {
        return new ResponseEntity<>(mapAccountToUser(account), HttpStatus.OK);
    }
}
