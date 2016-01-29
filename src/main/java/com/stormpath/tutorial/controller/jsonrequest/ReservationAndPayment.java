package com.stormpath.tutorial.controller.jsonrequest;

import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.reservations.db.Reservation;

/**
 * Created by tomasz.lelek on 29/01/16.
 */
public class ReservationAndPayment {
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation reservation;
    public Payment payment;

    public ReservationAndPayment(Reservation reservation, Payment payment) {

        this.reservation = reservation;
        this.payment = payment;
    }
}
