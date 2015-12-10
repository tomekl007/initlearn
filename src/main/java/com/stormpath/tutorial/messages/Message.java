package com.stormpath.tutorial.messages;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
public class Message {
    public final boolean read;
    public final String text;

    public Message(boolean read, String text) {
        this.read = read;
        this.text = text;
    }
}
