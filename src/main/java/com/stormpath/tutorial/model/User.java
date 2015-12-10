package com.stormpath.tutorial.model;

public class User {
    private final String email;
    private final String fullName;
    private final String givenName;
    private final String middleName;

    public User(String email, String fullName, String givenName, String middleName) {
        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.middleName = middleName;
    }
}
