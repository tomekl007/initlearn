package com.stormpath.tutorial.model;

import java.util.List;

public class User {
    public final String email;
    public final String fullName;
    public final String givenName;
    public final String middleName;
    public final String screenHero;
    public final Integer hourRate;
    public final String linkedIn;
    public final List<String> skills;

    public User(String email, String fullName, String givenName, String middleName, String screenHero, Integer hourRate,
                String linkedIn, List<String> skills) {
        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.screenHero = screenHero;
        this.hourRate = hourRate;
        this.linkedIn = linkedIn;
        this.skills = skills;
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
