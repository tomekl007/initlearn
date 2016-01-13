package com.stormpath.tutorial.reservations.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.teacher = :teacherEmail")
    List<Reservation> getAllTecherReservations(@Param("teacherEmail") String teacherEmail);
    @Query("SELECT r FROM Reservation r WHERE r.reserved_by = :reservedBy")
    List<Reservation> getAllUserAppoitments(@Param("reservedBy") String reservedBy);
}
