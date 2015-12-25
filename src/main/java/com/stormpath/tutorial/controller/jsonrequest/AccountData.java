package com.stormpath.tutorial.controller.jsonrequest;

/**
 * Created by tomasz.lelek on 25/12/15.
 */
public class AccountData {
    public String password;
    public String email;
    public String surname;
    public String givenName;

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", givenName='" + givenName + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
