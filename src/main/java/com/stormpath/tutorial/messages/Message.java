package com.stormpath.tutorial.messages;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
public class Message {
    public final boolean wasRead;
    public final String text;
    public final String from;
    public final Long timestamp;

    public Message(boolean wasRead, String text, String from, Long timestamp) {
        this.wasRead = wasRead;
        this.text = text;
        this.from = from;
        this.timestamp = timestamp;
    }
}
