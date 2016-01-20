package com.stormpath.tutorial.group;

import com.stormpath.tutorial.model.User;

import java.util.List;

/**
 * Created by tomasz.lelek on 20/01/16.
 */
public interface GroupCachableService {
    List<User> findAccountsForGroup(String groupName);
}
