package com.stormpath.tutorial.controller;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.stormpath.tutorial.payment.PaymentService;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/adaptivePayment")
    public String executeAdaptivePayment() throws IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        return "redirect:https://www.sandbox.paypal.com/webscr?cmd=_ap-payment&paykey=" + paymentService.pay();
    }

    @RequestMapping("/ap_chained_payment_cancel")
    public void paymentCancelled() {
        logger.info("payment cancelled");
    }

    @RequestMapping("/ap_chained_payment_success")
    public ResponseEntity paymentSucceed(HttpServletRequest request) throws URISyntaxException, IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        String referer = request.getHeader("referer");
        logger.info("payment success from referer" + referer);

        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(new URI(referer), "UTF-8");
        for (NameValuePair nameValuePair : nameValuePairs) {
            if (nameValuePair.getName().equals("paykey")) {
                PaymentDetailsResponse paymentStatus = paymentService.getPaymentStatus(nameValuePair.getValue());
                return ResponseEntity.ok("payment successfully status: " +
                        paymentStatus.getStatus() + ", sender: "
                        + paymentStatus.getSenderEmail() + ", sender: " + 
                          paymentStatus.getSender());
            }
        }
        return ResponseEntity.ok().build();
    }
}
