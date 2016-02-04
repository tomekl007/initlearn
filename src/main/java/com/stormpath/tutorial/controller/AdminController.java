package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.service.AdminService;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.Optional;

/**
 * Created by tomasz.lelek on 04/02/16.
 */
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @RequestMapping("/admin/invalidateCache")
    public ResponseEntity<String> invalidateCache(ServletRequest servletRequest) {
        Optional<User> user = getAdminUser(servletRequest);
        if (user.isPresent()) {
            boolean res = adminService.invalidateCaches();
            if (res) {
                return new ResponseEntity<>("Successfully invalidate caches", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("/admin/verifyTeacher/{email:.+}")
    public ResponseEntity<String> setTeacherAsVerified(ServletRequest servletRequest, @PathVariable("email") String teacherEmail) {
        Optional<User> user = getAdminUser(servletRequest);
        if (user.isPresent()) {
            Optional<Account> accountByEmail = userService.findAccountByEmail(teacherEmail);
            if (accountByEmail.isPresent()) {
                AccountUtils.addIsTeacherVerified(accountByEmail.get(), true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private Optional<User> getAdminUser(ServletRequest servletRequest) {
        return AccountUtils.getUserIfUserLoggedIn(servletRequest).filter(u -> u.email.equals("tomekl007@gmail.com"));
    }
}
