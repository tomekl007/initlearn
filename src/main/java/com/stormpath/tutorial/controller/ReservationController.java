package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.ReservationService;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getLoggedInUserAppointments(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, a ->
                reservationService.getAllAppointments(a.getEmail())
        );
    }

    private static final String dateFormat = ("dd/MM/yyyy-hh:mm:ss");
    @RequestMapping(value = "/reservations", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<Reservation>> reserveLesson(@RequestBody ReservationRequest reservationRequest,
                                                           ServletRequest servletRequest) {

        DateTime reservationTime = normalizeTime(reservationRequest);
        DateTime endOfReservationTime = getEndOfReservationTime(reservationTime);

        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> {
                    Optional<Account> teacherAccount = userService.findAccountByEmail(reservationRequest.teacher);
                    if (!teacherAccount.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>(
                            reservationService.reserve(a,
                                    teacherAccount.get(), reservationTime, endOfReservationTime), HttpStatus.OK);
                });
    }

    private DateTime getEndOfReservationTime(DateTime reservationTime) {
        return reservationTime.plusHours(1);
    }

    private DateTime normalizeTime(ReservationRequest reservationRequest) {
        DateTime dateTime = DateTimeFormat.forPattern(dateFormat)
                .parseDateTime(reservationRequest.fromHour);
        return dateTime.withTime(dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), 0, 0);
    }

}
