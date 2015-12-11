package com.stormpath.tutorial.utils;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

/**
 * Created by tomasz.lelek on 11/12/15.
 */
public class AccountUtils {
    public static void addCustomFieldToAccount(Account a, String name, String value) {
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
}
