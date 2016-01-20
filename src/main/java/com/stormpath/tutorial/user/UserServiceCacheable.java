package com.stormpath.tutorial.user;

import com.stormpath.tutorial.model.User;

import java.util.List;

/**
 * Created by tomasz.lelek on 19/01/16.
 */
public interface UserServiceCacheable {
    List<String> getAllSkillsAvailable();
    List<User> findUsersByEmail(String email);
}
