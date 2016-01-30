package com.stormpath.tutorial.messages;

import javax.persistence.*;
import java.util.LinkedHashMap;

/**
 * Created by tomasz.lelek on 10/12/15.
 */
@Entity
public class Message {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String text;
    public Long timestamp;
    public String from_email;
    public String to_email;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setFrom_email(String from_email) {
        this.from_email = from_email;
    }

    public String getFrom_email() {
        return from_email;
    }

    public String getTo_email() {
        return to_email;
    }

    public void setTo_email(String to_email) {
        this.to_email = to_email;
    }

    @Override

    public String toString() {
        return "Message{" +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", fromEmail='" + from_email + '\'' +
                ", toEmail='" + to_email + '\'' +
                '}';
    }

    public Message(String text, Long timestamp, String fromEmail, String toEmail) {
        this.text = text;
        this.timestamp = timestamp;
        this.from_email = fromEmail;
        this.to_email = toEmail;
    }

    public static Message mapFromLinkedHashMap(LinkedHashMap linkedHashMap) {
        String text = (String) linkedHashMap.get("text");
        Long timestamp = (Long) linkedHashMap.get("timestamp");
        String fromEmail = (String) linkedHashMap.get("fromEmail");
        String toEmail = (String) linkedHashMap.get("toEmail");
        return new Message(text, timestamp, fromEmail, toEmail);
    }

    public static LinkedHashMap toLinkedHashMap(Message message) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("text", message.text);
        linkedHashMap.put("timestamp", message.timestamp);
        linkedHashMap.put("fromEmail", message.from_email);
        linkedHashMap.put("toEmail", message.to_email);
        return linkedHashMap;
    }
}
