package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.DeleteReservationRequest;
import com.stormpath.tutorial.controller.jsonrequest.ReservationAndPayment;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.reservations.ReservationDeleteResult;
import com.stormpath.tutorial.reservations.ReservationService;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("email") String email,
                                                             @RequestParam(value = "from_hour", required = false) Optional<Long> fromHour) {
        List<Reservation> allReservations = reservationService.getAllReservations(email, fromHour);
        return new ResponseEntity<>(allReservations, HttpStatus.OK);
    }


    @RequestMapping(value = "/reservations/payments", method = RequestMethod.GET)
    public ResponseEntity<List<ReservationAndPayment>> getReservationsAndPayments(ServletRequest servletRequest,
                                                                                  @RequestParam(value = "from_hour", required = false) Optional<Long> fromHour) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> reservationService.getAllReservationsAndPayments(a.getEmail(), fromHour));
    }

    @RequestMapping(value = "/appointments/delete/{email:.+}", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> deleteAppointment(
            @PathVariable("email") String email,
            ServletRequest servletRequest,
            @RequestBody DeleteReservationRequest deleteReservationRequest,
            @RequestParam(value = "current_time") Long currentTime) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, a -> {
            ReservationDeleteResult result = reservationService.delete(a.getEmail(), email, deleteReservationRequest.fromHour, currentTime);
            if (result.equals(ReservationDeleteResult.NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (result.equals(ReservationDeleteResult.PAYMENT_ALREADY_COMPLETED)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(reservationService.getAllAppointments(a.getEmail(), Optional.of(currentTime)), HttpStatus.OK);
        });
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getLoggedInUserAppointments(ServletRequest servletRequest,
                                                                         @RequestParam(value = "from_hour", required = false) Optional<Long> fromHour) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, a ->
                reservationService.getAllAppointments(a.getEmail(), fromHour)
        );
    }

    @RequestMapping(value = "/appointments/payments", method = RequestMethod.GET)
    public ResponseEntity<List<ReservationAndPayment>> getAppointmentsAndPayments(ServletRequest servletRequest,
                                                                                  @RequestParam(value = "from_hour", required = false) Optional<Long> fromHour) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, a ->
                reservationService.getAllAppointmentsAndPayments(a.getEmail(), fromHour)
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


    @RequestMapping(value = "/reservations/delete/{email:.+}", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> deleteReservation(
            @PathVariable("email") String email,
            ServletRequest servletRequest,
            @RequestBody DeleteReservationRequest deleteReservationRequest,
            @RequestParam(value = "current_time") Long currentTime) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest, a -> {
            ReservationDeleteResult result = reservationService.delete(email, a.getEmail(), deleteReservationRequest.fromHour, currentTime);
            if (result.equals(ReservationDeleteResult.NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (result.equals(ReservationDeleteResult.PAYMENT_ALREADY_COMPLETED)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(reservationService.getAllReservations(a.getEmail(), Optional.of(currentTime)), HttpStatus.OK);
        });
    }


}
