package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.group.Group;
import com.stormpath.tutorial.group.GroupService;
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

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "group/users/{groupName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getTeachers(@PathVariable("groupName") String groupName) {

        Optional<AccountList> accountForGroup = groupService.findGroup(groupName).map(Group::getAccounts);

        if (!accountForGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(AccountUtils.mapToUsers(accountForGroup.get()), HttpStatus.OK);
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
