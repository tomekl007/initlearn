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
    public final List<String> links;
    public final String bio;
    public final String img;
    public final Double average;
    public final Integer numberOfRates;
    public final Boolean isATeacher;
    public final List<String> ratedBy;

    public User(String email, String fullName, String givenName, String middleName, String screenHero, Integer hourRate,
                String linkedIn, List<String> skills, List<String> links, String bio, String img, Double average, Integer numberOfRates, Boolean isATeacher, List<String> ratedBy) {
        this.email = email;
        this.fullName = fullName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.screenHero = screenHero;
        this.hourRate = hourRate;
        this.linkedIn = linkedIn;
        this.skills = skills;
        this.links = links;
        this.bio = bio;
        this.img = img;
        this.average = average;
        this.numberOfRates = numberOfRates;
        this.isATeacher = isATeacher;
        this.ratedBy = ratedBy;
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
