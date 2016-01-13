package com.stormpath.tutorial.reservations.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getAllTecherReservations(String teacherEmail);
    List<Reservation> getAllUserAppoitments(String reservedBy);
}
