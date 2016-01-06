package com.stormpath.tutorial.endtoend;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomasz.lelek on 05/01/16.
 */
public class TestRegisterAndLoginFlow extends IntegrationTestHelper{
    @Test
    public void shouldRegisterUser() throws IOException {
        HttpResponse execute = registerUserRequest("endToEndTest@gmail.com", "changeMeQuickly", "End", "ToEnd");

        //then
        assertThat(execute.getStatusLine().getStatusCode()).isEqualTo(200);
    }


    @Test
    public void shouldLoginUser() throws IOException {
        //when
        String token = loginUserRequest("endToEndTest@gmail.com", "changeMeQuickly");

        //then should return isLoggedIn using that token
        HttpResponse response = createHttpGetWithAuthorizationToken("isLoggedIn", token);

        //then
        String isLoggedIn = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        assertThat(isLoggedIn).isEqualTo("true");


        //and then should delete account
        HttpResponse responseDelete = deleteAccount(token);

        System.out.println(IOUtils.toString(responseDelete.getEntity().getContent()));

        assertThat(responseDelete.getStatusLine().getStatusCode()).isEqualTo(200);

    }
}
