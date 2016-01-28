package com.stormpath.tutorial.db.payment;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Payment {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    private String from_email;

    public Payment() {
    }

    public Payment(String from_email, String to_email, Double amount, Date payment_date, String payment_status,
                   Long reservation_id) {

        this.from_email = from_email;
        this.to_email = to_email;
        this.amount = amount;
        this.payment_date = payment_date;
        this.payment_status = payment_status;
        this.reservation_id = reservation_id;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    private String to_email;
    private Double amount;
    private Date payment_date;

    private String payment_status;

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    private Long reservation_id;
}
