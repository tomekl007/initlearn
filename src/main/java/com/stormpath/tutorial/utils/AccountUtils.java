package com.stormpath.tutorial.utils;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.tutorial.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountUtils {

    public static final String SCREEN_HERO_FIELD = "screenHero";
    public static final String HOUR_RATE_FIELD = "hourRate";
            
    public static void addScreenheroField(Account a, String value){
        addCustomFieldToAccount(a,SCREEN_HERO_FIELD, value);
        
    }
    
    public static void addHourRateForTeacher(Account a, Double value){
        addCustomFieldToAccount(a, HOUR_RATE_FIELD, value);
        
    }
    
    public static void addCustomFieldToAccount(Account a, String name, Object value) {
        CustomData customData = a.getCustomData();
        customData.put(name, value);
        a.save();
    }

    public static String getCustomFieldValue(Account a, String screenHeroField) {
        Object o = a.getCustomData().get(screenHeroField);
        if (o == null) {
            return "";
        } else {
            return (String) o;
        }
    }

    public static Double getCustomDoubleFieldValue(Account a, String screenHeroField) {
        Object o = a.getCustomData().get(screenHeroField);
        if (o == null) {
            return null;
        } else {
            return (Double) o;
        }
    }


    public static User mapAccountToUser(Account a) {
        return new User(a.getEmail(), a.getFullName(), a.getGivenName(), a.getMiddleName(),
                AccountUtils.getCustomFieldValue(a, SCREEN_HERO_FIELD), 
                AccountUtils.getCustomDoubleFieldValue(a, HOUR_RATE_FIELD));
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
}
