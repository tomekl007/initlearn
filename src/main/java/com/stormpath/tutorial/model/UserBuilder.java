package com.stormpath.tutorial.model;

import java.util.List;

public class UserBuilder {
    private String email;
    private String fullName;
    private String givenName;
    private String middleName;
    private String screenHero;
    private Integer hourRate;
    private String linkedIn;
    private List<String> skills;
    private List<String> links;
    private String bio;
    private String img;
    private Double average;
    private Integer numberOfRates;
    private Boolean isATeacher;

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserBuilder setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public UserBuilder setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public UserBuilder setScreenHero(String screenHero) {
        this.screenHero = screenHero;
        return this;
    }

    public UserBuilder setHourRate(Integer hourRate) {
        this.hourRate = hourRate;
        return this;
    }

    public UserBuilder setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public UserBuilder setSkills(List<String> skills) {
        this.skills = skills;
        return this;
    }

    public UserBuilder setLinks(List<String> links) {
        this.links = links;
        return this;
    }

    public UserBuilder setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserBuilder setImg(String img) {
        this.img = img;
        return this;
    }

    public UserBuilder setAverage(Double average) {
        this.average = average;
        return this;
    }

    public UserBuilder setNumberOfRates(Integer numberOfRates) {
        this.numberOfRates = numberOfRates;
        return this;
    }

    public UserBuilder setIsATeacher(boolean isATeacher) {
        this.isATeacher = isATeacher;
        return this;
    }

    public User createUser() {
        return new User(email, fullName, givenName, middleName, screenHero, hourRate,
                linkedIn, skills, links, bio, img, average, numberOfRates, isATeacher);
    }
}