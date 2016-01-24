package com.stormpath.tutorial.reservations.db;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
@Entity
public class Reservation {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    public Date from_hour;
    public String reserved_by;
    public String teacher;

    public Date getTo_hour() {
        return to_hour;
    }

    public void setTo_hour(Date to_hour) {
        this.to_hour = to_hour;
    }

    public void setReserved_by(String reserved_by) {
        this.reserved_by = reserved_by;
    }

    public Date to_hour;
    private String subject;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(Date from) {
        this.from_hour = from;
    }

    public String getReserved_by() {
        return reserved_by;
    }

    public void setReseved_by (String reservedBy) {
        this.reserved_by = reservedBy;
    }

    public Reservation() {

    }

    public Reservation(Date from_hour, String reserved_by, String teacher, Date to_hour, String subject) {
        this.from_hour = from_hour;
        this.reserved_by = reserved_by;
        this.teacher = teacher;
        this.to_hour = to_hour;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
