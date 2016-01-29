package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.DeleteReservationRequest;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.reservations.ReservationService;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/appointments/delete/{email:.+}", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> deleteAppointment(
            @PathVariable("email") String email,
            ServletRequest servletRequest,
            @RequestBody DeleteReservationRequest deleteReservationRequest) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, a -> {
            long result = reservationService.delete(a.getEmail(), email, deleteReservationRequest.fromHour);
            if (result == -1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(reservationService.getAllAppointments(a.getEmail()), HttpStatus.OK);
        });
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getLoggedInUserAppointments(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, a ->
                reservationService.getAllAppointments(a.getEmail())
        );
    }


    @RequestMapping(value = "/reservations", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<Reservation>> reserveLesson(@RequestBody ReservationRequest reservationRequest,
                                                           ServletRequest servletRequest) {

        DateTime reservationTime = reservationService.normalizeTime(reservationRequest);
        DateTime endOfReservationTime = reservationService.getEndOfReservationTime(reservationTime);

        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> {
                    Optional<Account> teacherAccount = userService.findAccountByEmail(reservationRequest.teacher);
                    if (!teacherAccount.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }

                    if (reservationService.alreadyReserved(reservationTime, endOfReservationTime, teacherAccount.get())) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }

                    return new ResponseEntity<>(
                            reservationService.reserve(a,
                                    teacherAccount.get(), reservationTime,
                                    endOfReservationTime, reservationRequest.subject), HttpStatus.OK);
                });
    }


    //todo make sure that delete only reservations that are after todays day, do not delete in past
    @RequestMapping(value = "/reservations/delete/{email:.+}", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> deleteReservation(
            @PathVariable("email") String email,
            ServletRequest servletRequest,
            @RequestBody DeleteReservationRequest deleteReservationRequest) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, a -> {
            long result = reservationService.delete(email, a.getEmail(), deleteReservationRequest.fromHour);
            if (result == -1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(reservationService.getAllReservations(a.getEmail()), HttpStatus.OK);
        });
    }


}
