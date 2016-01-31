package com.stormpath.tutorial.messages;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomasz.lelek on 31/01/16.
 */
@Entity
@Table(name = "Message")
public class MessageDb {
    public String text;
    public Date timestamp;
    public String fromEmail;
    public String toEmail;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public MessageDb(String text, Date timestamp, String fromEmail, String toEmail) {
        this.text = text;
        this.timestamp = timestamp;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
