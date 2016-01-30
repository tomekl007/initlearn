package com.stormpath.tutorial.payment;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.*;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.db.payment.PaymentStatus;
import com.stormpath.tutorial.db.payment.PaymentsRepository;
import com.stormpath.tutorial.db.payment.PaypalConfiguration;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.reservations.db.Reservation;
import com.stormpath.tutorial.reservations.db.ReservationRepository;
import com.stormpath.tutorial.user.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    public static final double COMPANY_PROVISION = 5.00;//todo move to configuration
    public static final String COMPANY_RECEIVER_EMAIL = "initlearn@gmail.com";
    @Autowired
    PaymentsRepository paymentsRepository;
    @Autowired
    PaypalConfiguration paypalConfiguration;
    @Autowired
    UserService userService;
    @Autowired
    ReservationRepository reservationRepository;


    public String pay(String senderEmail, String toEmail, Date fromHour) {
        Optional<Long> reservationId = getReservationId(senderEmail, toEmail, fromHour);//todo handle when none
        Optional<User> receiver = userService.findUserByEmail(toEmail);

        //todo The+amount+for+the+primary+receiver+must+be+greater+than+or+equal+to+the+total+of+other+chained+receiver+amounts
        Integer receiverHourRateInt = receiver.get().hourRate;
        if (receiverHourRateInt == null) {
            throw new RuntimeException("teacher hour rate is null");//todo handle it gracefully
        }

        Double receiverHourRate = receiverHourRateInt.doubleValue();

        PayRequest payRequest = new PayRequest();

        List<Receiver> receivers = new ArrayList<>();
        Receiver secondaryReceiver = createCompanyReceiver(COMPANY_RECEIVER_EMAIL);
        receivers.add(secondaryReceiver);

        createTeacherReceiver(toEmail, receivers, receiverHourRate);
        ReceiverList receiverList = new ReceiverList(receivers);

        payRequest.setReceiverList(receiverList);

        RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
        payRequest.setRequestEnvelope(requestEnvelope);
        payRequest.setActionType("PAY");
        payRequest.setCancelUrl(paypalConfiguration.getCancelurl());
        payRequest.setReturnUrl(paypalConfiguration.getSuccessurl());
        payRequest.setCurrencyCode("USD");
        payRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");//todo

        Map<String, String> sdkConfig = credentials();

        AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
        try {
            String payKey = adaptivePaymentsService.pay(payRequest).getPayKey();
            addPayKeyToPayment(reservationId.get(), payKey);
            createCompanyPendingPayment(senderEmail, reservationId.get(), payKey);
            return payKey;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createCompanyPendingPayment(String senderEmail, Long reservationId, String payKey) {
        paymentsRepository.save(new Payment(senderEmail,
                COMPANY_RECEIVER_EMAIL, COMPANY_PROVISION, DateTime.now().toDate(),
                PaymentStatus.PENDING.toString(), reservationId, payKey));
    }

    private void addPayKeyToPayment(Long reservationId, String payKey) {
        List<Payment> payments = paymentsRepository.getPaymentsForReservation(reservationId);
        for (Payment payment : payments) {
            payment.setPay_key(payKey);
            paymentsRepository.save(payment);
        }
    }


    public Optional<Long> getReservationId(String senderEmail, String toEmail, Date fromHour) {
        List<Reservation> reservations = reservationRepository.getReservations(senderEmail, toEmail, fromHour);
        if (reservations.size() > 0) {
            return Optional.of(reservations.get(0).getId());
        }
        return Optional.empty();
    }

    private void createTeacherReceiver(String toEmail, List<Receiver> receivers, Double receiverHourRate) {
        Receiver primaryReceiver = new Receiver();
        primaryReceiver.setAmount(receiverHourRate);
        primaryReceiver.setEmail(toEmail);
        primaryReceiver.setPrimary(true);
        receivers.add(primaryReceiver);
    }

    private Receiver createCompanyReceiver(String companyReceiverEmail) {
        Receiver secondaryReceiver = new Receiver();
        secondaryReceiver.setAmount(COMPANY_PROVISION);
        secondaryReceiver.setEmail(companyReceiverEmail);
        return secondaryReceiver;
    }

    public PaymentDetailsResponse getPaymentStatus(String payKey) throws IOException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ClientActionRequiredException, MissingCredentialException, HttpErrorException, InvalidCredentialException, InterruptedException {
        RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
        PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest(requestEnvelope);
        paymentDetailsRequest.setPayKey(payKey);

        Map<String, String> sdkConfig = credentials();

        AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
        return adaptivePaymentsService.paymentDetails(paymentDetailsRequest);
    }

    private Map<String, String> credentials() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", paypalConfiguration.getMode());
        sdkConfig.put("acct1.UserName", paypalConfiguration.getUsername());
        sdkConfig.put("acct1.Password", paypalConfiguration.getPassword());
        sdkConfig.put("acct1.Signature", paypalConfiguration.getSignature());
        sdkConfig.put("acct1.AppId", paypalConfiguration.getAppid());
        return sdkConfig;
    }

    public void logCancelPayment(Optional<String> payKey) throws IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        if (payKey.isPresent()) {
//            PaymentDetailsResponse paymentStatus = getPaymentStatus(payKey.get());
            List<Payment> paymentsForPayKey = paymentsRepository.getPaymentsForPayKey(payKey.get());
            for (Payment payment : paymentsForPayKey) {
                payment.setPayment_status(PaymentStatus.CANCELLED.toString());
                paymentsRepository.save(payment);
            }
        }
    }

    public void logSuccessPayment(Optional<String> payKey) {
        if (payKey.isPresent()) {
            List<Payment> paymentsForPayKey = paymentsRepository.getPaymentsForPayKey(payKey.get());
            for (Payment payment : paymentsForPayKey) {
                payment.setPayment_status(PaymentStatus.COMPLETED.toString());
                paymentsRepository.save(payment);
            }
        }
    }

    public List<Payment> getPayments(String email) {
        return paymentsRepository.getPaymentsOfUser(email);
    }

    public List<Payment> getReceivedPayments(String email) {
        return paymentsRepository.getReceivedPayments(email);
    }

    public void createPendingPayment(String fromEmail, String toEmail,
                                     Double hourRate, Long reservationId) {
        paymentsRepository.save(new Payment(fromEmail, toEmail,
                hourRate, DateTime.now().toDate(),
                PaymentStatus.PENDING.toString(), reservationId));
    }
}

