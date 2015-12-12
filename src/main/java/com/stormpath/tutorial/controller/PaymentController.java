package com.stormpath.tutorial.controller;

public class PaymentController {
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
    
    
    {"scope":"https://uri.paypal.com/services/subscriptions https://api.paypal.com/v1/payments/.* https://api.paypal.com/v1/vault/credit-card https://uri.paypal.com/services/applications/webhooks openid https://uri.paypal.com/payments/payouts https://api.paypal.com/v1/vault/credit-card/.*","nonce":"2015-12-12T17:07:20Z6zMxSX3yboXCz8FZhdFC3RgEX6yeJN-2g4oijwT-PJQ","access_token":"A101.1IbZxKzbEcBIW7UoMkHfE-c3XRmFB5oDPuXeAz3HHagZ_137187Nw1I8vvT9-9Zu.Cr4ybN9IagKMYQqMYoMEaadLv4q","token_type":"Bearer","app_id":"APP-80W284485P519543T","expires_in":32400}polpc00469:services tomasz.lelek
    
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

}
