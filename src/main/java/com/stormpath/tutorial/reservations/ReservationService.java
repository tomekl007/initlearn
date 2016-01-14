package com.stormpath.tutorial.reservations;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.db.ReservationRepository;
import com.stormpath.tutorial.user.UserService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
@Component
public class ReservationService {
    @Autowired
    UserService userService;
    @Autowired
    ReservationRepository reservationRepository;


    public List<Reservation> getAllReservations(String email) {
        Optional<Account> accountByEmail = userService.findAccountByEmail(email);
        if(!accountByEmail.isPresent()){
            return Collections.emptyList();
        }
        return reservationRepository.getAllUserAppoitments(email);
    }



    public List<Reservation> reserve(Account reservedBy, Account teacher, DateTime reservationTime, DateTime endOfReservationTime) {
        //todo handle when appoitment could not be reserved
        addReservation(reservedBy, teacher, reservationTime, endOfReservationTime);
        return getAllReservations(reservedBy.getEmail());
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
        DateTime dateTime = DateTimeFormat.forPattern(dateFormat)
                .parseDateTime(reservationRequest.fromHour);
        return dateTime.withTime(dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), 0, 0);
    }


    public DateTime getEndOfReservationTime(DateTime reservationTime) {
        if(reservationTime.getHourOfDay() == 23){//todo fix that hack
            return reservationTime.plusHours(1).plusDays(1);
        }
        return reservationTime.plusHours(1);
    }

    public boolean alreadyReserved(DateTime reservationTime, DateTime endOfReservationTime, Account teacher) {
        List<Reservation> allReservationsForTimespan = reservationRepository
                .getAllReservationsForTimespan(reservationTime.toDate(),
                        endOfReservationTime.toDate(), teacher.getEmail());
        return !allReservationsForTimespan.isEmpty();
    }
}

