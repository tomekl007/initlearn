package com.stormpath.tutorial.model;

import com.stormpath.sdk.account.AccountStatus;


public class User {
    private final String email;
    private final String fullName;
    private final String givenName;
    private final String middleName;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }

    public User(String email, String fullName, String givenName, String middleName) {
        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.middleName = middleName;
    }
}
