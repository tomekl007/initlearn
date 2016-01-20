package com.stormpath.tutorial.group;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.stormpath.tutorial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by tomasz.lelek on 20/01/16.
 */
@Component
public class GroupServiceWithCache implements GroupCachableService {
    @Autowired
    GroupCachableService groupService;

    private final Cache<String, List<User>> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private Callable<List<User>> getValueLoader(String groupName) {
        return () -> groupService.findAccountsForGroup(groupName);
    }

    @Override
    public List<User> findAccountsForGroup(String groupName) {
        return getFromCache(groupName);
    }

    private List<User> getFromCache(String gropName) {
        try {
            return cache.get(gropName, getValueLoader(gropName));
        } catch (ExecutionException e) {
            System.out.println("operation supplying value to cache thrown exception " + e);
            throw new RuntimeException(e);
        }
    }

    public void invalidate() {
        cache.invalidateAll();
    }
}
