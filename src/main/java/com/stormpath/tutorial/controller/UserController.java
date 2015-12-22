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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @RequestMapping(value = "users/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable("email") String email) {
        logger.info("find by email : " + email);
        List<User> users = userService.findUsersByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/screenhero", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addScreenHeroToUser(@RequestBody Nick nick, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addScreenheroField(a, nick.getNick()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/linkedin", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addLinkedInToUser(@RequestBody Link link, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addLinkedInField(a, link.getLink()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/hourRate", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addHourRateToUser(@RequestBody HourRate hourRate, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addHourRateForTeacher(a, hourRate.getHourRate()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }


    @RequestMapping(value = "users/{email:.+}/skills", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addSkillsToTeacher(@RequestBody Skills skills, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addSkillsForTeacher(a, skills.getSkills()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
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


    @RequestMapping(value = "users/{email:.+}/links", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addLinksToTeacher(@RequestBody Links links, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addLinksField(a, links.getLinks()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/bio", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addBioToTeacher(@RequestBody Bio bio, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addBioField(a, bio.getBio()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/img", method = RequestMethod.POST)
    public ResponseEntity<List<User>> addImgToTeacher(@RequestBody Link link, @PathVariable("email") String email) {
        List<Account> accountsByEmail = userService.findAccountsByEmail(email);
        accountsByEmail.forEach(a -> AccountUtils.addImgField(a, link.getLink()));
        return new ResponseEntity<>(AccountUtils.mapToUsers(accountsByEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "users/{email:.+}/rate", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<User>> addRateForTeacher(@RequestBody Rate rate, @PathVariable("email") String email) {
        List<User> users = userService.rateTeacher(rate.getRate(), email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping("/me")
    ResponseEntity<User> me(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, AccountUtils::mapAccountToUser);
    }

    @RequestMapping(value = "users/{email:.+}/teacher/data", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<User>> fillTeacherWithData(@RequestBody TeacherData teacherData, @PathVariable("email") String email){
        List<User> users = userService.fillTeacherWithData(teacherData, email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersBySkill(@RequestParam("skill") String skill){
        List<User> users = userService.findUsersBySkill(skill);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @RequestMapping("/isLoggedIn")
    ResponseEntity<Boolean> isLoggedIn(ServletRequest servletRequest){
        Optional<User> accountIfUserLoggedIn = AccountUtils.getAccountIfUserLoggedIn(servletRequest);
        if(accountIfUserLoggedIn.isPresent()){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
