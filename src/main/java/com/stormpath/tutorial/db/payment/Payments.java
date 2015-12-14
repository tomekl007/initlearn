package com.stormpath.tutorial.db.payment;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Payments {
    
    private String from_email;

    public Payments() {
    }

    public Payments(String from_email, String to_email, Double amount, Date payment_date) {

        this.from_email = from_email;
        this.to_email = to_email;
        this.amount = amount;
        this.payment_date = payment_date;
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
}
