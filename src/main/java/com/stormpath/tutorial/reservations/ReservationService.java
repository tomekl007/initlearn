package com.stormpath.tutorial.reservations;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.db.ReservationRepository;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
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
        return reservationRepository.getAllTecherReservations(email);
    }



    public List<Reservation> reserve(Account reservedBy, Account teacher, DateTime reservationTime) {
        //todo handle when appoitment could not be reserved
        addReservation(reservedBy, teacher, reservationTime);
        return getAllReservations(reservedBy.getEmail());
    }

    private void addReservation(Account reservedBy, Account teacher, DateTime reservationTime) {
        Reservation reservation = new Reservation(reservationTime.toDate(), reservedBy.getEmail(), teacher.getEmail());
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllAppointments(String email) {
        return reservationRepository.getAllUserAppoitments(email);
    }
}
