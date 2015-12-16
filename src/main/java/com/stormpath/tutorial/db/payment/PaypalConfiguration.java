package com.stormpath.tutorial.db.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by tomasz.lelek on 16/12/15.
 */
@Component
@ConfigurationProperties(prefix = "paypal")
public class PaypalConfiguration {
    private String url;
    private String cancelurl;
    private String mode;
    private String appid;
    private String password;
    private String username;
    private String signature;


    public String getSuccessurl() {
        return successurl;
    }

    public void setSuccessurl(String successurl) {
        this.successurl = successurl;
    }

    public String getCancelurl() {
        return cancelurl;
    }

    public void setCancelurl(String cancelurl) {
        this.cancelurl = cancelurl;
    }

    private String successurl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
