package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.*;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Optional<String> sort,
                                                  @RequestParam(required = false, defaultValue = "1") Optional<Integer> sortOrder,
                                                  @RequestParam(required = false) Optional<Integer> offset) {
        return new ResponseEntity<>(userService.getAllUsers(sort, sortOrder, offset), HttpStatus.OK);
    }


    @RequestMapping(value = "users/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable("email") String email) {
        logger.info("find by email : " + email);
        List<User> users = userService.findUsersByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "users/screenhero", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addScreenHeroToUser(@RequestBody ScreenHero screenHero, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addScreenheroField(a, screenHero.getScreenHero()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/linkedin", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addLinkedInToUser(@RequestBody Link link, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addLinkedInField(a, link.getLink()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/hourRate", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addHourRateToUser(@RequestBody HourRate hourRate, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addHourRateForTeacher(a, hourRate.getHourRate()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }


    @RequestMapping(value = "users/skills", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addSkillsToTeacher(@RequestBody Skills skills, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addSkillsForTeacher(a, skills.getSkills()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/{email:.+}/skills", method = RequestMethod.GET)
    public ResponseEntity<List<List<String>>> getSkillsForTeacher(@PathVariable("email") String email) {
        List<List<String>> list = userService.findAccountsByEmail(email)
                .stream()
                .map(AccountUtils::mapAccountToUser)
                .map(u -> u.skills)
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "users/links", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addLinksToTeacher(@RequestBody Links links, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addLinksField(a, links.getLinks()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/bio", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addBioToTeacher(@RequestBody Bio bio, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addBioField(a, bio.getBio()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/img", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addImgToTeacher(@RequestBody Link link, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, account -> {
            List<Account> accountsByEmail = Collections.singletonList(account);
            accountsByEmail.forEach(a -> AccountUtils.addImgField(a, link.getLink()));
            return AccountUtils.mapToUsers(accountsByEmail);
        });
    }

    @RequestMapping(value = "users/{email:.+}/rate", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<User>> addRateForTeacher(@RequestBody Rate rate, @PathVariable("email") String email,
                                                        ServletRequest servletRequest) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, userThatRate -> {
            if(userService.alreadyRateThatTeacher(userThatRate, email)){
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }

            List<User> users = userService.rateTeacher(userThatRate, rate.getRate(), email);
            return new ResponseEntity<>(users, HttpStatus.OK);
        });
    }

    @RequestMapping("/me")
    ResponseEntity<List<User>> me(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> Collections.singletonList(AccountUtils.mapAccountToUser(a)));
    }

    @RequestMapping(value = "users/data", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<User>> fillTeacherWithData(@RequestBody TeacherData teacherData, ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest,
                account -> userService.fillTeacherWithData(teacherData, account.getEmail()));
    }

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersBySkill(@RequestParam("skill") String skill) {
        List<User> users = userService.findUsersBySkill(skill);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping("/isLoggedIn")
    ResponseEntity<Boolean> isLoggedIn(ServletRequest servletRequest) {
        Optional<User> accountIfUserLoggedIn = AccountUtils.getUserIfUserLoggedIn(servletRequest);
        if (accountIfUserLoggedIn.isPresent()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @RequestMapping("/skills")
    ResponseEntity<List<String>> getAllSkills() {
        List<String> allSkillsAvailable = userService.getAllSkillsAvailable();
        return new ResponseEntity<>(allSkillsAvailable, HttpStatus.OK);
    }
}
