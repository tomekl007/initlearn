package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.reservations.Appointment;
import com.stormpath.tutorial.reservations.Reservation;
import com.stormpath.tutorial.reservations.ReservationService;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by tomasz.lelek on 11/01/16.
 */
@Controller
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/reservations/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("email") String email) {
        List<Reservation> allReservations = reservationService.getAllReservations(email);
        return new ResponseEntity<>(allReservations, HttpStatus.OK);
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getLoggedInUserAppointments(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, a ->
                reservationService.getAllAppointments(a)
        );
    }

    @RequestMapping(value = "/reservations/{email:.+}", method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> reserveLesson(@PathVariable("email") String email,
                                                           ServletRequest servletRequest) {
        DateTime reservationTime = DateTime.now();//todo get from api
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> {
                    Optional<Account> teacherAccount = userService.findAccountByEmail(email);
                    if (!teacherAccount.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>(
                            reservationService.reserve(a, teacherAccount.get(), reservationTime), HttpStatus.OK);
                });
    }

}
