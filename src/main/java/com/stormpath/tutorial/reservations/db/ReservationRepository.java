package com.stormpath.tutorial.reservations.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.teacher = :teacherEmail ORDER BY r.from_hour")
    List<Reservation> getAllTecherReservations(@Param("teacherEmail") String teacherEmail);

    @Query("SELECT r FROM Reservation r WHERE r.reserved_by = :reservedBy ORDER BY r.from_hour")
    List<Reservation> getAllUserAppoitments(@Param("reservedBy") String reservedBy);

    //todo fix that condition
    @Query("SELECT r FROM Reservation r WHERE r.teacher = :teacherEmail AND" +
            "( r.from_hour >= :from_hour AND r.from_hour <= :to_hour " +
            " OR " +
            "r.to_hour >= :from_hour AND r.to_hour <= :to_hour )")
    List<Reservation> getAllReservationsForTimespan(@Param("from_hour") Date fromHour, @Param("to_hour") Date toHour,
                                                    @Param("teacherEmail") String teacherEmail);

    @Query("SELECT r FROM Reservation r WHERE r.reserved_by = :reservedBy AND r.teacher = :teacherEmail AND " +
            "r.from_hour = :fromHour")
    Reservation getReservation(@Param("reservedBy") String reservedBy, @Param("teacherEmail") String teacherEmail,
                               @Param("fromHour") Date fromHour);
}
