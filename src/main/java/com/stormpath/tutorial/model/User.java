package com.stormpath.tutorial.model;

import com.stormpath.sdk.account.AccountStatus;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
public class User {
    private final String email;
    private final String fullName;
    private final String givenName;
    private final AccountStatus status;
    private final String middleName;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", status=" + status +
                ", middleName='" + middleName + '\'' +
                '}';
    }

    public User(String email, String fullName, String givenName, AccountStatus status, String middleName) {

        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.status = status;
        this.middleName = middleName;
    }
}
