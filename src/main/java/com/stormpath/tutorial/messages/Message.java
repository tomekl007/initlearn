package com.stormpath.tutorial.messages;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
public class Message {
    public final boolean wasRead;
    public final String text;
    public final Long timestamp;
    public final String fromEmail;
    public final String toEmail;

    public Message(boolean wasRead, String text, Long timestamp, String fromEmail, String toEmail) {
        this.wasRead = wasRead;
        this.text = text;
        this.timestamp = timestamp;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }
}
