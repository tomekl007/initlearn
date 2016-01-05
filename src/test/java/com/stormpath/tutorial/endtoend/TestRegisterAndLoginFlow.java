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
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomasz.lelek on 05/01/16.
 */
public class TestRegisterAndLoginFlow {


    @Test
    public void shouldRegisterUser() throws IOException {
        //given
        String json = "{ \"email\": \"endToEndTest@gmail.com\",\n" +
                "        \"givenName\": \"End\",\n" +
                "        \"grant_type\": \"password\",\n" +
                "        \"password\": \"changeMeQuickly\",\n" +
                "        \"surname\": \"ToEnd\"}";

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://initlearn.herokuapp.com/registerAccount");
        post.setEntity(new StringEntity(json));
        post.setHeader("Content-Type", "application/json");

        //when
        HttpResponse execute = httpClient.execute(post);

        //then
        assertThat(execute.getStatusLine()).isEqualTo(200);


    }

    @Test
    public void shouldLoginUser() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://initlearn.herokuapp.com/oauth/token");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Host", "initlearn.herokuapp.com");
        post.setHeader("Origin", "https://initlearn.herokuapp.com");
        post.setHeader("Referer", "https://initlearn.herokuapp.com/");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        urlParameters.add(new BasicNameValuePair("password", "changeMeQuickly"));
        urlParameters.add(new BasicNameValuePair("username", "endToEndTest@gmail.com"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        //when
        HttpResponse execute = httpClient.execute(post);

        //then
        String token = IOUtils.toString(execute.getEntity().getContent(), "UTF-8");

        //and should return isLoggedIn using that token
        HttpGet httpGet = new HttpGet("https://initlearn.herokuapp.com/isLoggedIn");
        httpGet.setHeader("Authorization", token);

        //when
        HttpResponse response = httpClient.execute(httpGet);

        //then
        String isLoggedIn = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        assertThat(isLoggedIn).isEqualTo("true");


        //and then should delete account
        HttpDelete delete = new HttpDelete("https://initlearn.herokuapp.com/deleteAccount");
        delete.setHeader("Authorization", token);

        HttpResponse responseDelete = httpClient.execute(delete);

        System.out.println(responseDelete);

    }

}
