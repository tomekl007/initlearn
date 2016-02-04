package com.stormpath.tutorial.controller.jsonrequest;

import java.util.List;

/**
 * Created by tomasz.lelek on 22/12/15.
 */
public class TeacherData {
    public String screenHero;
    public Integer hourRate;
    public String linkedIn;
    public List<String> skills;
    public List<String> links;
    public String bio;
    public String paypalEmail;

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getScreenHero() {
        return screenHero;
    }

    public void setScreenHero(String screenHero) {
        this.screenHero = screenHero;
    }

    public Integer getHourRate() {
        return hourRate;
    }

    public void setHourRate(Integer hourRate) {
        this.hourRate = hourRate;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String img;
}
