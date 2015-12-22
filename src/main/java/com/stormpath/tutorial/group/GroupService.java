package com.stormpath.tutorial.group;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.group.Group;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
@Component
public class GroupService {

    @Autowired
    private Client client;
    public static final String TEACHERS_GROUP_NAME = "teachers";

    public Optional<Group> findGroup(String groupName) {
        return StreamSupport.stream(Spliterators
                .spliteratorUnknownSize(client.getGroups(Collections.singletonMap("name", groupName))
                        .iterator(), Spliterator.ORDERED), false).findFirst();

    }

    public List<User> addGroupToAccounts(List<Account> accounts, Optional<Group> group) {
        if (group.isPresent()) {
            addGroupToAccounts(group.get(), accounts);
        }

        return AccountUtils.mapToUsers(accounts);
    }

    private void addGroupToAccounts(Group g, List<Account> accounts) {
        for (Account account : accounts) {
            account.addGroup(g);
            account.save();
        }
    }

    public List<User> findAllTeachers() {
        return findGroup(TEACHERS_GROUP_NAME)
                .map(Group::getAccounts)
                .map(AccountUtils::mapToUsers)
                .orElse(Collections.emptyList());
    }
}

