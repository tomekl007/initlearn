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
public class UserService implements AccountFields, UserServiceCacheable {

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

    public Optional<User> findUserByEmail(String email) {
        List<Account> accounts = findAccountsByEmail(email);
        List<User> users = AccountUtils.mapToUsers(accounts);
        if(users.size() == 0 ){
            return Optional.empty();
        }else{
            return Optional.of(users.get(0));
        }
    }

    public List<Account> findAccountsByEmail(String email) {
        return findAccountsBy("email", email);
    }

    public Optional<Account> findAccountByEmail(String email) {
        List<Account> accounts = findAccountsBy("email", email);
        if(accounts.size() == 0 ){
            return Optional.empty();
        }else{
            return Optional.of(accounts.get(0));
        }
    }

    private List<Account> findAccountsBy(String key, String value) {
        List<Account> accounts = new ArrayList<>();
        client.getAccounts(Collections.singletonMap(key, value))
                .iterator()
                .forEachRemaining(accounts::add);
        return accounts;
    }

    public List<User> rateTeacher(Account userThatRate, Integer newValue, String email) {
        Map<Account, User> usersByEmail = findUsersAndAccountByEmail(email);
        for (Map.Entry<Account, User> entry : usersByEmail.entrySet()) {
            Double currentAverage = Optional.ofNullable(entry.getValue().average).orElse(newValue.doubleValue());
            Integer numberOfRates = Optional.ofNullable(entry.getValue().numberOfRates).orElse(1);

            double newAverageRate = AverageCountStrategy.countApproxAverage(currentAverage, newValue, numberOfRates);
            logger.info("rateTeacher, new rate: " + newAverageRate);
            addNewRateAndAverage(entry.getKey(), numberOfRates, newAverageRate, userThatRate);
        }
        return new ArrayList<>(findUsersAndAccountByEmail(email).values());
    }

    private void addNewRateAndAverage(Account a, Integer numberOfRates, double newAverageRate, Account userThatRate) {
        AccountUtils.addAverageForTeacher(a, newAverageRate);
        AccountUtils.addNumberOfRateForTeacher(a, numberOfRates + 1);
        AccountUtils.addRatedBy(a, userThatRate);
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

    @Override
    public List<User> getAllUsers() {
        return getAllUsers(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public List<User> getAllUsers(Optional<String> sort,
                                  Optional<Integer> sortOrder, Optional<Integer> offset) {
        return getAllUsers(sort, sortOrder, offset, client);
    }

    public static List<User> getAllUsers(Optional<String> sort, Optional<Integer> sortOrder,
                                         Optional<Integer> offset, Client client) {
        List<Account> list = new ArrayList<>();
        client.getAccounts(pagination(offset))
                .iterator()
                .forEachRemaining(list::add);
        return sortBy(AccountUtils.mapToUsers(list), sort, sortOrder);
    }

    private static List<User> sortBy(List<User> list, Optional<String> sort, Optional<Integer> sortOrder) {
        if(sort.isPresent() && sortOrder.isPresent()) {
           AccountUtils.sortUsersBy(list, sort.get(), sortOrder.get());
        }
        return list;
    }

    private static AccountCriteria pagination(Optional<Integer> offset) {
        if (offset.isPresent()) {
            logger.info("--> offset : " + offset);
            return Accounts.criteria().offsetBy(offset.get());
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

    public List<String> getAllSkillsAvailable() {
        return getAllUsers()
                .stream()
                .map(u -> u.skills)
                .filter( s -> !s.isEmpty())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public boolean alreadyRateThatTeacher(Account userThatRate, String email) {
        Optional<User> userByEmail = findUserByEmail(email);
        if(userByEmail.isPresent()){
            User user = userByEmail.get();
            return user.ratedBy.contains(userThatRate.getEmail());
        }else{
            return false;
        }
    }
}
