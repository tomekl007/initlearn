package com.stormpath.tutorial.utils;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.tutorial.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountUtils {

    public static final String SCREEN_HERO_FIELD = "screenHero";
    public static final String HOUR_RATE_FIELD = "hourRate";
    public static final String SKILLS_FIELD = "skills";
    public static final String LINKEDIN_FIELD = "linkedIn";
    public static final String LINKS_FILED = "links";
    public static final String BIO_FILED = "bio";

    public static void addBioField(Account a, String bio){
        addCustomFieldToAccount(a, BIO_FILED, bio);
    }
    
    public static void addLinksField(Account a, List<String> values){
        addCustomListFieldToAccount(a, LINKS_FILED, values, a.getCustomData());
    }
    
    public static void addScreenheroField(Account a, String value) {
        addCustomFieldToAccount(a, SCREEN_HERO_FIELD, value);

    }

    public static void addHourRateForTeacher(Account a, Double value) {
        addCustomFieldToAccount(a, HOUR_RATE_FIELD, value);

    }
    
    public static void addSkillsForTeacher(Account a, List<String> skills){
        addCustomListFieldToAccount(a, SKILLS_FIELD, skills, a.getCustomData());
    }

    public static void addLinkedInField(Account a, String link) {
        addCustomFieldToAccount(a, LINKEDIN_FIELD, link);
    }
    

    public static void addCustomListFieldToAccount(Account a, String fieldName, List<String> skills, 
                                                   Map<String, Object> customData) {
        Object o = customData.get(fieldName);
        List<String> userSkills = new LinkedList<>();
        if(o != null){
            userSkills.addAll((List<String>) o);
        }
        userSkills.addAll(skills);
        customData.put(fieldName, userSkills);
        a.save();
    }

    public static void addCustomFieldToAccount(Account a, String name, Object value) {
        CustomData customData = a.getCustomData();
        customData.put(name, value);
        a.save();
    }

    public static String getCustomFieldValue(Account a, String field) {
        Object o = a.getCustomData().get(field);
        if (o == null) {
            return "";
        } else {
            return (String) o;
        }
    }

    public static Integer getCustomIntegerValue(Account a, String field) {
        Object o = a.getCustomData().get(field);
        if (o == null) {
            return null;
        } else {
            return (int) o;
        }
    }
    
    public static List<String> getCustomListFieldValue(Account a, String fieldName) {
        Object o = a.getCustomData().get(fieldName);
        if (o == null) {
            return Collections.emptyList();
        } else {
            return (List<String>) o;
        }
    }


    public static User mapAccountToUser(Account a) {
        return new User(a.getEmail(), a.getFullName(), a.getGivenName(), a.getMiddleName(),
                AccountUtils.getCustomFieldValue(a, SCREEN_HERO_FIELD),
                AccountUtils.getCustomIntegerValue(a, HOUR_RATE_FIELD), 
                AccountUtils.getCustomFieldValue(a, LINKEDIN_FIELD),
                AccountUtils.getCustomListFieldValue(a, SKILLS_FIELD),
                AccountUtils.getCustomListFieldValue(a, LINKS_FILED),
                AccountUtils.getCustomFieldValue(a, BIO_FILED));
    }


    public static List<User> mapToUsers(List<Account> list) {
        return list
                .stream()
                .map(AccountUtils::mapAccountToUser).collect(Collectors.toList());
    }


    public static List<User> mapToUsers(AccountList list) {
        List<Account> accounts = new ArrayList<>();
        list.iterator().forEachRemaining(accounts::add);
        return mapToUsers(accounts);
    }


    public static Optional<User> getAccountIfUserLoggedIn(ServletRequest servletRequest) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return Optional.of(mapAccountToUser(authenticatedAccount));
        } else {
            return Optional.empty();
        }
    }

    public static <T> ResponseEntity<T> actionForAuthenticatedUserOrRedirectToLogin(ServletRequest servletRequest, Function<Account, T> action) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return new ResponseEntity<>(action.apply(authenticatedAccount), HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
        }
    }

}
