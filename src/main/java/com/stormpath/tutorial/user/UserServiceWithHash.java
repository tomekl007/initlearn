package com.stormpath.tutorial.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.stormpath.tutorial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by tomasz.lelek on 19/01/16.
 */
@Component
public class UserServiceWithHash implements UserServiceCacheable {
    @Autowired
    UserService userService;

    private static final int ONE_MILLION = 1000000;

    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(ONE_MILLION)
            .build();


    @Override
    public List<User> getAllUsers() {
        try {
            return Collections.emptyList();
//            return cache.get(clientId, getValueLoader(clientId));
        } catch (Exception e) {
            System.out.println("operation supplying value to cache thrown exception " + e);
            throw new RuntimeException(e);
        }
    }

    private Callable<List<User>> getValueLoader() {
        return () -> userService.getAllUsers();
    }
}
