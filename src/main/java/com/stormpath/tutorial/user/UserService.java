package com.stormpath.tutorial.user;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountCriteria;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.account.Accounts;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.tutorial.avarage.AverageCountStrategy;
import com.stormpath.tutorial.controller.jsonrequest.AccountData;
import com.stormpath.tutorial.controller.jsonrequest.TeacherData;
import com.stormpath.tutorial.group.GroupService;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountFields;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tomasz.lelek on 16/12/15.
 */
@Component
public class UserService implements AccountFields {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Client client;

    @Autowired
    private GroupService groupService;

    @Autowired
    Application application;

    public Map<Account, User> findUsersAndAccountByEmail(String email) {
        List<Account> accounts = findAccountsByEmail(email);
        Map<Account, User> map = new HashMap<>();
        for (Account account : accounts) {
            map.put(account, AccountUtils.mapAccountToUser(account));
        }
        return map;
    }

    public List<User> findUsersByEmail(String email) {
        List<Account> accounts = findAccountsByEmail(email);
        return AccountUtils.mapToUsers(accounts);
    }

    public List<Account> findAccountsByEmail(String email) {
        return findAccountsBy("email", email);
    }

    private List<Account> findAccountsBy(String key, String value) {
        List<Account> accounts = new ArrayList<>();
        client.getAccounts(Collections.singletonMap(key, value))
                .iterator()
                .forEachRemaining(accounts::add);
        return accounts;
    }

    public List<User> rateTeacher(Integer newValue, String email) {
        Map<Account, User> usersByEmail = findUsersAndAccountByEmail(email);
        for (Map.Entry<Account, User> entry : usersByEmail.entrySet()) {
            Double currentAverage = Optional.ofNullable(entry.getValue().average).orElse(newValue.doubleValue());
            Integer numberOfRates = Optional.ofNullable(entry.getValue().numberOfRates).orElse(1);

            double newAverageRate = AverageCountStrategy.countApproxAverage(currentAverage, newValue, numberOfRates);
            logger.info("rateTeacher, new rate: " + newAverageRate);
            addNewRateAndAverage(entry.getKey(), numberOfRates, newAverageRate);
        }
        return new ArrayList<>(usersByEmail.values());
    }

    private void addNewRateAndAverage(Account a, Integer numberOfRates, double newAverageRate) {
        AccountUtils.addAverageForTeacher(a, newAverageRate);
        AccountUtils.addNumberOfRateForTeacher(a, numberOfRates + 1);
    }

    public List<User> fillTeacherWithData(TeacherData teacherData, String email) {
        List<Account> accountsByEmail = findAccountsByEmail(email);
        for (Account account : accountsByEmail) {
            AccountUtils.addBioField(account, teacherData.bio);
            AccountUtils.addHourRateForTeacher(account, teacherData.hourRate);
            AccountUtils.addSkillsForTeacher(account, teacherData.skills);
            AccountUtils.addImgField(account, teacherData.img);
            AccountUtils.addScreenheroField(account, teacherData.screenHero);
            AccountUtils.addLinksField(account, teacherData.links);
            AccountUtils.addLinkedInField(account, teacherData.linkedIn);
        }
        return AccountUtils.mapToUsers(accountsByEmail);
    }

    public List<User> findUsersBySkill(String skill) {
        return findAccountsByBruteForce(SKILLS_FIELD, skill);
    }

    //todo replace when will be resolved https://github.com/stormpath/stormpath-sdk-java/issues/221
    private List<User> findAccountsByBruteForce(String skillsField, String skill) {
        return groupService
                .findAllTeachers() //todo it search only teachers
                .stream()
                .filter(u -> u.skills.contains(skill.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers(Optional<String> sort, Optional<Integer> offset, Optional<Integer> limit) {
        return getAllUsers(sort, offset, limit, client);
    }

    public static List<User> getAllUsers(Optional<String> sort, Optional<Integer> offset, Optional<Integer> limit, Client client) {
        List<Account> list = new ArrayList<>();
        client.getAccounts(pagination(offset, limit))
                .iterator()
                .forEachRemaining(list::add);
        return AccountUtils.mapToUsers(sortBy(list, sort));
    }

    private static List<Account> sortBy(List<Account> list, Optional<String> sort) {

        return list;
    }

    private static AccountCriteria pagination(Optional<Integer> offset, Optional<Integer> limit) {
        if (offset.isPresent() && limit.isPresent()) {
            logger.info("--> offset : " + offset + " limit : " + limit);
            return Accounts.criteria().limitTo(limit.get()).offsetBy(offset.get());
        } else {
            return Accounts.criteria();
        }
    }

    public Account createAccount(AccountData accountData) {
        Account account = client.instantiate(Account.class)
                .setUsername(accountData.email)
                .setEmail(accountData.email)
                .setGivenName(accountData.givenName)
                .setSurname(accountData.surname)
                .setPassword(accountData.password)
                .setStatus(AccountStatus.ENABLED);


        return application.createAccount(account);
    }
}
