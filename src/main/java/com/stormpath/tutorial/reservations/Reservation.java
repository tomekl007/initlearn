package com.stormpath.tutorial.reservations;

import org.joda.time.DateTime;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
public class Reservation {
    public final DateTime from;
    public final String reservedBy;

    public Reservation(DateTime from, String reservedBy) {
        this.from = from;
        this.reservedBy = reservedBy;
    }
}
