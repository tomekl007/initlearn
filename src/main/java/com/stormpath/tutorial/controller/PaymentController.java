package com.stormpath.tutorial.controller;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.stormpath.tutorial.db.payment.PaypalConfiguration;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.payment.PaymentService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    private PaymentService paymentService;
    @Autowired
    PaypalConfiguration paypalConfiguration;

    @RequestMapping("/adaptivePayment")
    public String executeAdaptivePayment(ServletRequest servletRequest, @RequestParam("toEmail") String toEmail){
        Optional<User> accountIfUserLoggedIn = AccountUtils.getUserIfUserLoggedIn(servletRequest);
        if(accountIfUserLoggedIn.isPresent()) {
            return "redirect:"+ paypalConfiguration.getUrl() + paymentService.pay(accountIfUserLoggedIn.get(), toEmail);
        }
        else{
            return "redirect:/login";
        }
    }

    @RequestMapping("/ap_chained_payment_cancel")
    public String paymentCancelled(HttpServletRequest request) throws URISyntaxException, IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        logger.info("payment cancelled");
        Optional<String> payKey = getPayKeyValue(request);
        paymentService.logCancelPayment(payKey);

        return "redirect:/#/cancelPayment";
    }

    @RequestMapping("/ap_chained_payment_success")
    public String paymentSucceed(HttpServletRequest request) throws URISyntaxException, IOException, InvalidResponseDataException, SSLConfigurationException, OAuthException, MissingCredentialException, InvalidCredentialException, HttpErrorException, ClientActionRequiredException, InterruptedException {
        Optional<String> payKey = getPayKeyValue(request);
        paymentService.logSuccessPayment(payKey);


        return "redirect:/#/successPayment";
    }


    public Optional<String> getPayKeyValue(HttpServletRequest httpServletRequest) throws URISyntaxException {
        String referer = httpServletRequest.getHeader("referer");
        logger.info("payment success from referer" + referer);
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(new URI(referer), "UTF-8");
        for (NameValuePair nameValuePair : nameValuePairs) {
            if (nameValuePair.getName().equals("paykey")) {
                return Optional.of(nameValuePair.getValue());
            }
        }
        return Optional.empty();
    }
}
