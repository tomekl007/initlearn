package com.stormpath.tutorial.reservations;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
public class Appointment {
    public final String with;
    public final Long from;

    public Appointment(String with, Long from) {
        this.with = with;
        this.from = from;
    }
}
