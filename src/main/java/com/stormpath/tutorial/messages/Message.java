package com.stormpath.tutorial.messages;

import java.util.LinkedHashMap;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
public class Message {
    public final String text;
    public final Long timestamp;
    public final String fromEmail;
    public final String toEmail;

    @Override
    public String toString() {
        return "Message{" +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", fromEmail='" + fromEmail + '\'' +
                ", toEmail='" + toEmail + '\'' +
                '}';
    }

    public Message(String text, Long timestamp, String fromEmail, String toEmail) {
        this.text = text;
        this.timestamp = timestamp;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public static Message mapFromLinkedHashMap(LinkedHashMap linkedHashMap) {
        String text = (String) linkedHashMap.get("text");
        Long timestamp = (Long) linkedHashMap.get("timestamp");
        String fromEmail = (String) linkedHashMap.get("fromEmail");
        String toEmail = (String) linkedHashMap.get("toEmail");
        return new Message(text, timestamp, fromEmail, toEmail);
    }
}
