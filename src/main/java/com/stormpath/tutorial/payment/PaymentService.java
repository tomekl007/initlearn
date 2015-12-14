package com.stormpath.tutorial.payment;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.stormpath.tutorial.db.payment.Payment;
import com.stormpath.tutorial.db.payment.PaymentsRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentService {
    @Autowired
    PaymentsRepository paymentsRepository;
    //todo besides sending callback to paypal we need to send transation data to db
    /*
     REST :
    Sandbox test AppID:
    APP-80W284485P519543T

    initlearn@gmail.com
    Client AaCujwXFfX5qA3SrHYZfk9xyA4xJa6nhSA97ZK-Rkf9Fziz_oJogKvPNYet3mrLnm1E-7FKUeCae7Zbi
    Secret EC16AItooDDLyTdHdpy4wQEZVhdCDECUdKkiNcUanhSos5Pj49XVRKCmZnxj5V7aes7xSBOIN-Q7Y8GC


    curl -v https://api.sandbox.paypal.com/v1/oauth2/token \
    -H "Accept: application/json" \
    -H "Accept-Language: en_US" \
    -u "AaCujwXFfX5qA3SrHYZfk9xyA4xJa6nhSA97ZK-Rkf9Fziz_oJogKvPNYet3mrLnm1E-7FKUeCae7Zbi:EC16AItooDDLyTdHdpy4wQEZVhdCDECUdKkiNcUanhSos5Pj49XVRKCmZnxj5V7aes7xSBOIN-Q7Y8GC" \
    -d "grant_type=client_credentials"


    {"scope":"https://uri.paypal.com/services/subscriptions https://api.paypal.com/v1/payments/.* https://api.paypal.com/v1/vault/credit-card https://uri.paypal.com/services/applications/webhooks openid https://uri.paypal.com/payments/payouts https://api.paypal.com/v1/vault/credit-card/.*","nonce":"2015-12-12T17:07:20Z6zMxSX3yboXCz8FZhdFC3RgEX6yeJN-2g4oijwT-PJQ","access_token":"A101.1IbZxKzbEcBIW7UoMkHfE-c3XRmFB5oDPuXeAz3HHagZ_137187Nw1I8vvT9-9Zu.Cr4ybN9IagKMYQqMYoMEaadLv4q","token_type":"Bearer","app_id":"APP-80W284485P519543T","expires_in":32400}

    curl -v https://api.sandbox.paypal.com/v1/payments/payment \
    -H 'Content-Type: application/json' \
    -H 'Authorization: Bearer A101.1IbZxKzbEcBIW7UoMkHfE-c3XRmFB5oDPuXeAz3HHagZ_137187Nw1I8vvT9-9Zu.Cr4ybN9IagKMYQqMYoMEaadLv4q' \
    -d '{
    "intent":"sale",
        "redirect_urls":{
            "return_url":"http://example.com/your_redirect_url.html",
            "cancel_url":"http://example.com/your_cancel_url.html"
        },
    "payer":{
        "payment_method":"paypal"
    },
    "transactions":[
        {
          "amount":{
            "total":"7.47",
            "currency":"USD"
        }
        }
    ]
    }'



    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Username: initlearn_api1.gmail.com
    Password: WBZVH3YQDZ7SHD63
    Signature: Ac6hQx6YxXj3U9NYzyzh9SMGUYPFAYr72W1iN0KX4-oI175jNybgaeu6


    curl -s --insecure https://api-3t.sandbox.paypal.com/nvp -d "USER=initlearn_api1.gmail.com&PWD=WBZVH3YQDZ7SHD63&SIGNATURE=Ac6hQx6YxXj3U9NYzyzh9SMGUYPFAYr72W1iN0KX4-oI175jNybgaeu6&METHOD=SetExpressCheckout&VERSION=98&PAYMENTREQUEST_0_AMT=10&PAYMENTREQUEST_0_CURRENCYCODE=USD&PAYMENTREQUEST_0_PAYMENTACTION=SALE&cancelUrl=http://www.example.com/cancel.html&returnUrl=http://www.example.com/success.html"

    */



    public String pay() throws IOException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ClientActionRequiredException, MissingCredentialException, HttpErrorException, InvalidCredentialException, InterruptedException {
        
        String toEmail = "pupil2@gmail.com";
        paymentsRepository.save(new Payment("from", toEmail, 10.00d, DateTime.now().toDate()));
        
        PayRequest payRequest = new PayRequest();

        List<Receiver> receivers = new ArrayList<Receiver>();
        Receiver secondaryReceiver = new Receiver();
        secondaryReceiver.setAmount(5.00);
        secondaryReceiver.setEmail("initlearn@gmail.com");
        receivers.add(secondaryReceiver);

        Receiver primaryReceiver = new Receiver();
        primaryReceiver.setAmount(10.00);
        primaryReceiver.setEmail(toEmail);
        primaryReceiver.setPrimary(true);
        receivers.add(primaryReceiver);
        ReceiverList receiverList = new ReceiverList(receivers);

        payRequest.setReceiverList(receiverList);

        RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
        payRequest.setRequestEnvelope(requestEnvelope);
        payRequest.setActionType("PAY");
        payRequest.setCancelUrl("https://initlearn.herokuapp.com/ap_chained_payment_cancel");
        payRequest.setReturnUrl("https://initlearn.herokuapp.com/ap_chained_payment_success");
        payRequest.setCurrencyCode("USD");
        payRequest.setIpnNotificationUrl("http://replaceIpnUrl.com");//todo

        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", "sandbox");
        sdkConfig.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
        sdkConfig.put("acct1.Password", "WX4WTU3S8MY44S7F");
        sdkConfig.put("acct1.Signature","AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
        sdkConfig.put("acct1.AppId","APP-80W284485P519543T");

        AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
        PayResponse payResponse = adaptivePaymentsService.pay(payRequest);

        return payResponse.getPayKey();
    }
}
