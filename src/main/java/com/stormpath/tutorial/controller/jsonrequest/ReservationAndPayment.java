package com.stormpath.tutorial.controller.jsonrequest;

import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.reservations.db.Reservation;

/**
 * Created by tomasz.lelek on 29/01/16.
 */
public class ReservationAndPayment {
    private final Reservation reservation;
    private final Payment payment;

    public ReservationAndPayment(Reservation reservation, Payment payment) {

        this.reservation = reservation;
        this.payment = payment;
    }
}
