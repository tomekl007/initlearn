package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.group.Group;
import com.stormpath.tutorial.group.GroupService;
import com.stormpath.tutorial.group.GroupServiceWithCache;
import com.stormpath.tutorial.model.User;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupServiceWithCache groupServiceWithCache;

    @RequestMapping(value = "group/users/{groupName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getTeachers(@PathVariable("groupName") String groupName,
                                                  @RequestParam(value = "verified", defaultValue = "true") Boolean isVerified) {

        List<User> accountForGroup = groupServiceWithCache.findAccountsForGroup(groupName);
        accountForGroup.stream().filter(u -> {
            if (u.isTeacherVerified == null) {
                return false;
            } else {
                return u.isTeacherVerified.equals(isVerified);
            }
        }).collect(Collectors.toList());

        if (accountForGroup.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accountForGroup, HttpStatus.OK);
    }

    @RequestMapping(value = "users/{group}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<User>> addUserToGroup(@PathVariable("group") String group, ServletRequest servletRequest) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            Optional<Group> groupOptional = groupService.findGroup(group);

            if (!groupOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Account> accountsByEmail = Collections.singletonList(account);
            List<User> users = groupService.addGroupToAccounts(accountsByEmail, groupOptional);
            return new ResponseEntity<>(users, HttpStatus.OK);
        });
    }
}
