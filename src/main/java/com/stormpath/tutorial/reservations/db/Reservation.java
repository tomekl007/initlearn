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

    public Date get_from_hour() {
        return from_hour;
    }

    public void set_from_hour(Date from) {
        this.from_hour = from;
    }

    public String get_reserved_by() {
        return reserved_by;
    }

    public void set_reseved_by (String reservedBy) {
        this.reserved_by = reservedBy;
    }

    public Reservation() {

    }

    public Reservation(Date from_hour, String reserved_by, String teacher) {
        this.from_hour = from_hour;
        this.reserved_by = reserved_by;
        this.teacher = teacher;
    }
}
