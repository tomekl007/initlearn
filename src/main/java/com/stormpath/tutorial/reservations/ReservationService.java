package com.stormpath.tutorial.reservations;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.db.ReservationRepository;
import com.stormpath.tutorial.user.UserService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
@Component
public class ReservationService {
    @Autowired
    UserService userService;
    @Autowired
    ReservationRepository reservationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    public List<Reservation> getAllReservations(String email) {
        Optional<Account> accountByEmail = userService.findAccountByEmail(email);
        if(!accountByEmail.isPresent()){
            return Collections.emptyList();
        }
        return reservationRepository.getAllTecherReservations(email);
    }



    public List<Reservation> reserve(Account reservedBy, Account teacher, DateTime reservationTime, DateTime endOfReservationTime) {
        //todo handle when appoitment could not be reserved
        addReservation(reservedBy, teacher, reservationTime, endOfReservationTime);
        return getAllAppointments(reservedBy.getEmail());
    }

    private void addReservation(Account reservedBy, Account teacher, DateTime reservationTime, DateTime endOfReservationTime) {
        Reservation reservation =
                new Reservation(reservationTime.toDate(), reservedBy.getEmail(),
                        teacher.getEmail(), endOfReservationTime.toDate());
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllAppointments(String email) {
        return reservationRepository.getAllUserAppoitments(email);
    }

    private static final String dateFormat = ("dd/MM/yyyy-HH:mm");

    public DateTime normalizeTime(ReservationRequest reservationRequest) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reservationRequest.fromHour);
        Date time = calendar.getTime();
        DateTime dateTime = new DateTime(time);
        return dateTime.withTime(dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), 0, 0);
    }


    public DateTime getEndOfReservationTime(DateTime reservationTime) {
        return reservationTime.plusHours(1);
    }

    public boolean alreadyReserved(DateTime reservationTime, DateTime endOfReservationTime, Account teacher) {
        List<Reservation> allReservationsForTimespan = reservationRepository
                .getAllReservationsForTimespan(reservationTime.toDate(),
                        endOfReservationTime.toDate(), teacher.getEmail());
        return !allReservationsForTimespan.isEmpty();
    }

    public void deleteReservation(String reservedBy, String teacherEmail, Long fromHour) {
        logger.info("delete for reserved by: " + reservedBy + " teacher: " + teacherEmail + " fromHour: " + fromHour);
        Reservation reservation = reservationRepository.getReservation(reservedBy, teacherEmail, new Date(fromHour));
        reservationRepository.delete(reservation.getId());
    }
}

