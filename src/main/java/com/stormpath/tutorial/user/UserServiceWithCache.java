package com.stormpath.tutorial.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class UserServiceWithCache implements UserServiceCacheable {
    @Autowired
    UserService userService;

    private final Cache<String, List<String>> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();


    private Callable<List<String>> getValueLoader() {
        return () -> userService.getAllSkillsAvailable();
    }

    @Override
    public List<String> getAllSkillsAvailable() {
        return getFromCache();
    }

    private List<String> getFromCache() {
        try {
            return cache.get("skills", getValueLoader());
        } catch (ExecutionException e) {
            System.out.println("operation supplying value to cache thrown exception " + e);
            throw new RuntimeException(e);
        }
    }
}
