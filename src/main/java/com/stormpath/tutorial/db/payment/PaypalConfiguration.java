package com.stormpath.tutorial.db.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tomasz.lelek on 16/12/15.
 */
@ConfigurationProperties(prefix = "paypal")
public class PaypalConfiguration {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
