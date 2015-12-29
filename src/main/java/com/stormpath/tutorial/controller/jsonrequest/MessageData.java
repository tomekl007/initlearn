package com.stormpath.tutorial.controller.jsonrequest;

/**
 * Created by tomasz.lelek on 29/12/15.
 */
public class MessageData {
    public String emailTo;
    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmailTo() {

        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
}
