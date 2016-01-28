package com.stormpath.tutorial.db.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p from Payment p where p.from_email = :from_email ORDER BY payment_date")
    List<Payment> getPaymentsOfUser(@Param("from_email") String from_email);

    @Query("SELECT p from Payment p where p.to_email = :to_email ORDER BY payment_date")
    List<Payment> getReceivedPayments(@Param("to_email") String email);

    @Query("SELECT p from Payment p where p.reservation_id = :reservation_id")
    Payment getPaymentForReservation(@Param("reservation_id") long reservationId);
}
