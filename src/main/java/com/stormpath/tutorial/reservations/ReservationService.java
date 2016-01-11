package com.stormpath.tutorial.reservations;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.tutorial.messages.Message;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
@Component
public class ReservationService {
    public static final String appointmentsField = "appointments";
    public static final String reservationField = "reservations";
    @Autowired
    UserService userService;


    public List<Reservation> getAllReservations(String email) {
        Optional<Account> accountByEmail = userService.findAccountByEmail(email);
        if(!accountByEmail.isPresent()){
            return Collections.emptyList();
        }
        return getAllReservationsField(accountByEmail.get());
    }


    public List<Reservation> getAllReservationsField(Account account) {
        Object reservation = account.getCustomData().get(reservationField);
        if (reservation == null) {
            return Collections.emptyList();
        } else {
            return (List<Reservation>) reservation;
        }
    }

    public List<Appointment> getAllAppointments(Account a) {
        Object reservation = a.getCustomData().get(appointmentsField);
        if (reservation == null) {
            return Collections.emptyList();
        } else {
            return (List<Appointment>) reservation;
        }
    }

    public List<Appointment> reserve(Account reservedBy, Account teacher, DateTime reservationTime) {
        addReservation(reservedBy, teacher, reservationTime);
        return addAppointment(reservedBy, teacher, reservationTime);
    }

    private List<Appointment> addAppointment(Account reservedBy, Account teacher, DateTime reservationTime) {
        Appointment appointment = new Appointment(teacher.getEmail(), reservationTime.getMillis());

        CustomData customData = reservedBy.getCustomData();

        List<Appointment> appointmentList;
        Object appointments = customData.get(appointmentsField);
        if (appointments == null) {
            appointmentList = new LinkedList<>();
        } else {
            appointmentList = (List<Appointment>) appointment;
        }
        appointmentList.add(appointment);

        customData.put(appointmentsField, appointmentList);
        reservedBy.save();
        return appointmentList;
    }

    private void addReservation(Account reservedBy, Account teacher, DateTime reservationTime) {
        Reservation reservation = new Reservation(reservationTime.getMillis(), reservedBy.getEmail());

        addToList(teacher, reservation, reservationField);
    }

    private static<T> void addToList(Account account, T toAdd, String fieldName) {
        CustomData customData = account.getCustomData();

        List<T> reservationList;
        Object reservations = customData.get(fieldName);
        if (reservations == null) {
            reservationList = new LinkedList<>();
        } else {
            reservationList = (List<T>) reservations;
        }
        reservationList.add(toAdd);

        customData.put(fieldName, reservationList);
        account.save();
    }
}
