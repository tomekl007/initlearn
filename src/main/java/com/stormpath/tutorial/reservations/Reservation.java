package com.stormpath.tutorial.reservations;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
public class Reservation {
    public final Long from;
    public final String reservedBy;

    public Reservation(Long from, String reservedBy) {
        this.from = from;
        this.reservedBy = reservedBy;
    }
}
