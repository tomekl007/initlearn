package com.stormpath.tutorial.endtoend;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomasz.lelek on 06/01/16.
 */
public class IntegrationTestHelper {

    public static final String ORIGIN = "https://initlearn.herokuapp.com";
    private static final String APPLICATION_URL = "https://initlearn.herokuapp.com";
    private static final String HOST = "initlearn.herokuapp.com";
    HttpClient httpClient = HttpClients.createDefault();

    public HttpResponse registerUserRequest(String email, String password, String givenName, String surname)
            throws IOException {
        String json = "{ \"email\": \"" + email + "\",\n" +
                "        \"givenName\": \"" + givenName + "\",\n" +
                "        \"grant_type\": \"password\",\n" +
                "        \"password\": \"" + password + "\",\n" +
                "        \"surname\": \"" + surname + "\"}";

        HttpPost post = new HttpPost(APPLICATION_URL + "/registerAccount");
        post.setEntity(new StringEntity(json));
        post.setHeader("Content-Type", "application/json");
        return httpClient.execute(post);
    }


    public String loginUserRequest(String username, String password) throws IOException {
        HttpPost post = new HttpPost(APPLICATION_URL + "/oauth/token");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Host", HOST);
        post.setHeader("Origin", ORIGIN);
        post.setHeader("Referer", APPLICATION_URL + "/");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        urlParameters.add(new BasicNameValuePair("password", password));
        urlParameters.add(new BasicNameValuePair("username", username));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse execute = httpClient.execute(post);
        return IOUtils.toString(execute.getEntity().getContent(), "UTF-8");
    }


    public HttpResponse deleteAccount(String token) throws IOException {
        HttpDelete delete = new HttpDelete(APPLICATION_URL + "/deleteAccount");
        delete.setHeader("Authorization", token);

        return httpClient.execute(delete);
    }


    public HttpResponse createHttpGetWithAuthorizationToken(String suffix, String token) throws IOException {
        HttpGet httpGet = new HttpGet(APPLICATION_URL + "/" + suffix);
        httpGet.setHeader("Authorization", token);
        return httpClient.execute(httpGet);
    }
}
