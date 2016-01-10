package com.stormpath.tutorial.controller.jsonrequest;

import com.stormpath.tutorial.messages.Message;

/**
 * Created by tomasz.lelek on 02/01/16.
 */
public class MessageOverview {
    public final String emailTo;
    public final String userFullName;
    public final Message lastMessage;

    public MessageOverview(String emailTo, String userFullName, Message lastMessage) {
        this.emailTo = emailTo;
        this.userFullName = userFullName;
        this.lastMessage = lastMessage;
    }
}
