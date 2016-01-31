package com.stormpath.tutorial.reservations;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.ReservationAndPayment;
import com.stormpath.tutorial.controller.jsonrequest.ReservationRequest;
import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.db.payment.PaymentStatus;
import com.stormpath.tutorial.db.payment.PaymentsRepository;
import com.stormpath.tutorial.payment.PaymentService;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.db.ReservationRepository;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
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
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentsRepository paymentsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    public List<Reservation> getAllReservations(String email, Optional<Long> fromHour) {
        if (!fromHour.isPresent()) {
            return reservationRepository.getAllTecherReservations(email);
        } else {
            Date date = new Date(fromHour.get());
            Optional<Account> accountByEmail = userService.findAccountByEmail(email);
            if (!accountByEmail.isPresent()) {
                return Collections.emptyList();
            }
            return reservationRepository.getAllTecherReservationsFromHour(email, date);
        }
    }


    public List<Reservation> reserve(Account reservedBy, Account teacher,
                                     DateTime reservationTime, DateTime endOfReservationTime,
                                     String subject) {
        //todo handle when appoitment could not be reserved
        addReservation(reservedBy, teacher, reservationTime, endOfReservationTime, subject);
        return getAllReservations(teacher.getEmail(), Optional.empty());
    }

    private void addReservation(Account reservedBy, Account teacher, DateTime reservationTime,
                                DateTime endOfReservationTime, String subject) {
        Reservation reservation =
                new Reservation(reservationTime.toDate(), reservedBy.getEmail(),
                        teacher.getEmail(), endOfReservationTime.toDate(), subject);
        Reservation savedReservation = reservationRepository.save(reservation);

        Double hourRate = AccountUtils.mapAccountToUser(teacher).hourRate.doubleValue();
        paymentService.createPendingPayment(reservedBy.getEmail(), teacher.getEmail(), hourRate,
                savedReservation.getId());
    }

    public List<Reservation> getAllAppointments(String email, Optional<Long> fromHour) {
        if (!fromHour.isPresent()) {
            return reservationRepository.getAllUserAppoitments(email);
        } else {
            Date date = new Date(fromHour.get());
            return reservationRepository.getAllUserAppoitmentsFromHour(email, date);
        }

    }

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

    public ReservationDeleteResult delete(String reservedBy, String teacherEmail, Long fromHour, Long currentTime) {
        if (couldNotDeletePastReservation(fromHour, currentTime))
            return ReservationDeleteResult.CAN_NOT_DELETE_FROM_PAST;


        logger.info("delete for reserved by: " + reservedBy + " teacher: " + teacherEmail + " fromHour: " + fromHour);
        List<Reservation> reservations = reservationRepository.getReservations(reservedBy, teacherEmail, new Date(fromHour));
        if (reservations == null || reservations.isEmpty()) {
            return ReservationDeleteResult.NOT_FOUND;
        }
        for (Reservation reservation : reservations) {
            long reservationId = reservation.getId();
            List<Payment> paymentForReservation = paymentsRepository.getPaymentsForReservation(reservationId);
            if (paymentForReservation == null || paymentForReservation.isEmpty()) {
                return ReservationDeleteResult.NOT_FOUND;
            }
            for (Payment payment : paymentForReservation) {
                if (payment.getPayment_status().equals(PaymentStatus.COMPLETED.toString())) {
                    return ReservationDeleteResult.PAYMENT_ALREADY_COMPLETED;
                }
                paymentsRepository.delete(payment);
            }
            reservationRepository.delete(reservationId);
        }
        return ReservationDeleteResult.OK;
    }

    private boolean couldNotDeletePastReservation(Long fromHour, Long currentTime) {
        if (fromHour <= currentTime) {
            return true;
        }
        return false;
    }

    public List<ReservationAndPayment> getAllReservationsAndPayments(String email, Optional<Long> fromHour) {
        List<ReservationAndPayment> reservationAndPayments = new LinkedList<>();
        List<Reservation> allReservations = getAllReservations(email, fromHour);
        for (Reservation reservation : allReservations) {
            Payment paymentForReservation = paymentsRepository.getNotCompanyPaymentForReservation(reservation.getId());
            reservationAndPayments.add(new ReservationAndPayment(reservation, paymentForReservation));
        }
        return reservationAndPayments;

    }

    public List<ReservationAndPayment> getAllAppointmentsAndPayments(String email, Optional<Long> fromHour) {
        List<ReservationAndPayment> reservationAndPayments = new LinkedList<>();
        List<Reservation> allReservations = getAllAppointments(email, fromHour);
        for (Reservation reservation : allReservations) {
            Payment paymentForReservation = paymentsRepository.getNotCompanyPaymentForReservation(reservation.getId());
            reservationAndPayments.add(new ReservationAndPayment(reservation, paymentForReservation));
        }
        return reservationAndPayments;
    }
}

