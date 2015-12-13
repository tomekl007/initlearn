package com.stormpath.tutorial.controller;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.stormpath.tutorial.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private PaymentService paymentService = new PaymentService();
    @RequestMapping("/adaptivePayment")
    public String executeAdaptivePayment() throws IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        return "redirect:/https://www.sandbox.paypal.com/webscr?cmd=_ap-payment&paykey="+paymentService.pay();
    }
    
    @RequestMapping("/ap_chained_payment_cancel")
    public void paymentCancelled(){
        logger.info("payment cancelled");
    }
    
    @RequestMapping("/ap_chained_payment_success")
    public void paymentSucceed(){
        logger.info("payment success");   
    }
}
