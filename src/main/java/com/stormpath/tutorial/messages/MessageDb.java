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
    public String from_email;
    public String to_email;

    public MessageDb() {
    }

    @Id

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public MessageDb(String text, Date timestamp, String fromEmail, String toEmail) {
        this.text = text;
        this.timestamp = timestamp;
        this.from_email = fromEmail;
        this.to_email = toEmail;
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


    public String getFrom_email() {

        return from_email;
    }

    public void setFrom_email(String from_email) {
        this.from_email = from_email;
    }

    public String getTo_email() {
        return to_email;
    }

    public void setTo_email(String to_email) {
        this.to_email = to_email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
