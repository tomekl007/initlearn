package com.stormpath.tutorial.model;

public class User {
    public final String email;
    public final String fullName;
    public final String givenName;
    public final String middleName;
    public final String screenHero;

    public User(String email, String fullName, String givenName, String middleName, String screenHero) {
        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.screenHero = screenHero;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
