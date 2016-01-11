package com.stormpath.tutorial.reservations;

import org.joda.time.DateTime;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
public class Appointment {
    public final String with;
    public final DateTime from;

    public Appointment(String with, DateTime from) {
        this.with = with;
        this.from = from;
    }
}
