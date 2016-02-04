package com.stormpath.tutorial.service;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.group.GroupServiceWithCache;
import com.stormpath.tutorial.user.UserServiceWithCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    GroupServiceWithCache groupServiceWithCache;
    @Autowired
    UserServiceWithCache userServiceWithCache;

    @PreAuthorize("hasRole(@roles.ADMIN)")
    public boolean invalidateCaches() {
        groupServiceWithCache.invalidate();
        userServiceWithCache.invalidate();
        return true;
    }
}
