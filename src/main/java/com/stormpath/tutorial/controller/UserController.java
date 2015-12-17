package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.group.Group;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.user.UserService;
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

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private Client client;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<Account> list = new ArrayList<>();
        client.getAccounts().iterator().forEachRemaining(list::add);
        List<User> users = AccountUtils.mapToUsers(list);
        logger.info("Return users : " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @RequestMapping(value = "users/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable("email") String email) {
        logger.info("find by email : " + email);
        List<User> users = userService.findUsersByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
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

        return new ResponseEntity<>(AccountUtils.mapToUsers(accountForGroup.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/screenhero", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addScreenHeroToUser(@RequestBody String sc, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addScreenheroField(a, sc));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/hourRate", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addHourRateToUser(@RequestBody Double hourRate, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addHourRateForTeacher(a, hourRate));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }


    @RequestMapping(value = "users/{email:.+}/skills", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addSkillToTeacher(@RequestBody List<String> skill, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addSkillForTeacher(a, skill));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }
    
    
    @RequestMapping("/me")
    ResponseEntity<User> me(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrRedirectToLogin(servletRequest, AccountUtils::mapAccountToUser);
    }
}
