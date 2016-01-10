package com.stormpath.tutorial.payment;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.*;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.db.payment.PaymentsRepository;
import com.stormpath.tutorial.db.payment.PaypalConfiguration;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
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
    @Autowired
    PaymentsRepository paymentsRepository;
    @Autowired
    PaypalConfiguration paypalConfiguration;
    @Autowired
    UserService userService;


    public String pay(User sender, String toEmail) {

        Optional<User> receiver = userService.findUserByEmail(toEmail);
        Double receiverHourRate = receiver.get().hourRate.doubleValue();//todo handle absence

        paymentsRepository.save(new Payment(sender.email, toEmail, receiverHourRate, DateTime.now().toDate()));

        PayRequest payRequest = new PayRequest();

        List<Receiver> receivers = new ArrayList<>();
        Receiver secondaryReceiver = createCompanyReceiver();
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
            return adaptivePaymentsService.pay(payRequest).getPayKey();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createTeacherReceiver(String toEmail, List<Receiver> receivers, Double receiverHourRate) {
        Receiver primaryReceiver = new Receiver();
        primaryReceiver.setAmount(receiverHourRate);
        primaryReceiver.setEmail(toEmail);
        primaryReceiver.setPrimary(true);
        receivers.add(primaryReceiver);
    }

    private Receiver createCompanyReceiver() {
        Receiver secondaryReceiver = new Receiver();
        secondaryReceiver.setAmount(COMPANY_PROVISION);
        secondaryReceiver.setEmail("initlearn@gmail.com");
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
}
