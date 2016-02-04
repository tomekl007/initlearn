package com.stormpath.tutorial.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.stormpath.tutorial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class UserServiceWithCache implements UserServiceCacheable {
    @Autowired
    UserServiceCacheable userService;

    private final Cache<String, List<String>> skillsCache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(10, TimeUnit.MINUTES)//todo maybe endpoint for revalidating cache
            .build();

    private final Cache<String, List<User>> usersCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();


    private Callable<List<String>> getSkillsValueLoader() {
        return () -> userService.getAllSkillsAvailable();
    }

    @Override
    public List<String> getAllSkillsAvailable() {
        return getSkillsFromCache();
    }

    private List<String> getSkillsFromCache() {
        try {
            return skillsCache.get("skills", getSkillsValueLoader());
        } catch (ExecutionException e) {
            System.out.println("operation supplying value to cache thrown exception " + e);
            throw new RuntimeException(e);
        }
    }

    public void invalidate() {
        skillsCache.invalidateAll();
    }


    @Override
    public List<User> findUsersByEmail(String email) {
        try {
            return usersCache.get(email, getUsersValueLoader(email));
        } catch (ExecutionException e) {
            System.out.println("operation supplying value to cache thrown exception " + e);
            throw new RuntimeException(e);
        }
    }

    private Callable<List<User>> getUsersValueLoader(String email) {
        return () -> userService.findUsersByEmail(email);
    }
}
