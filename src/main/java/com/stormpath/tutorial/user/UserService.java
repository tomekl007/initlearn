package com.stormpath.tutorial.user;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.tutorial.avarage.AverageCountStrategy;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by tomasz.lelek on 16/12/15.
 */
@Component
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Client client;

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
        List<Account> accounts = new ArrayList<>();
        client.getAccounts(Collections.singletonMap("email", email))
                .iterator()
                .forEachRemaining(accounts::add);
        return accounts;
    }

    public List<User> rateTeacher(Integer newValue, String email) {
        Map<Account, User> usersByEmail = findUsersAndAccountByEmail(email);
        for (Map.Entry<Account, User> entry : usersByEmail.entrySet()) {
            Double currentAverage = Optional.ofNullable(entry.getValue().average).orElse(0d);
            Integer numberOfRates = Optional.ofNullable(entry.getValue().numberOfRates).orElse(0);

            double newAverageRate = AverageCountStrategy.countApproxAverage(currentAverage, newValue, numberOfRates);

            addNewRateAndAverage(entry.getKey(), numberOfRates, newAverageRate);
        }
        return new ArrayList<>(usersByEmail.values());
    }

    private void addNewRateAndAverage(Account a, Integer numberOfRates, double newAverageRate) {
        AccountUtils.addAverageForTeacher(a, newAverageRate);
        AccountUtils.addNumberOfRateForTeacher(a, numberOfRates + 1);
    }
}
