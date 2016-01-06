package com.stormpath.tutorial.endtoend;

import org.apache.http.HttpResponse;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomasz.lelek on 06/01/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMessagesFlow {

    IntegrationTestHelper firstUserHelper = new IntegrationTestHelper();
    IntegrationTestHelper secondUserHelper = new IntegrationTestHelper();
    String emailFirstUser = "testMessageUser1@gmail.com";
    String passwordFirstUser = "thisNeedTobeChanged1";

    String emailSecondUser = "testMessageUser2@gmail.com";
    String passwordSecondUser = "thisNeedTobeChanged2";

    String firstUserToken;
    String secondUserToken;

    @Before
    public void setup() throws IOException {
        firstUserHelper.registerUserRequest(emailFirstUser, passwordFirstUser, "A", "man");
        secondUserHelper.registerUserRequest(emailSecondUser, passwordSecondUser, "B", "man");

        firstUserToken = firstUserHelper.loginUserRequest(emailFirstUser, passwordFirstUser);
        System.out.println(firstUserToken);
        secondUserToken = secondUserHelper.loginUserRequest(emailSecondUser, passwordSecondUser);
        System.out.println(secondUserToken);
    }

    @Test
    public void firstUserShouldSendMessagesToOtherUserAndThatMessageShouldBeInConversationInBothUsers() throws IOException {
        //given
        String message = "from A man to B man" + new Random().nextInt(1000000);
        //when
        HttpResponse httpResponse = firstUserHelper.sendMessageToUser(firstUserToken, emailSecondUser, message);
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);

        //then
        String messagesForConversationWith = firstUserHelper.getMessagesForConversationWith(firstUserToken, emailSecondUser);
        assertThat(messagesForConversationWith).contains(message);

        String messagesForConversationWith1 = secondUserHelper.getMessagesForConversationWith(secondUserToken, emailFirstUser);
        assertThat(messagesForConversationWith1).contains(message);

    }

    @Test
    public void secondUserShouldReplyToFirstUser() throws IOException {
        String message = "from B man to A man" + new Random().nextInt(1000000);
        HttpResponse httpResponse = secondUserHelper.sendMessageToUser(secondUserToken, emailFirstUser, message);
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);

        //then
        String messagesForConversationWith = firstUserHelper.getMessagesForConversationWith(firstUserToken, emailSecondUser);

        assertThat(messagesForConversationWith).contains(message);

        String messagesForConversationWith1 = secondUserHelper.getMessagesForConversationWith(secondUserToken, emailFirstUser);
        assertThat(messagesForConversationWith1).contains(message);

    }


    @Test
    public void xtearDown() throws IOException {
        System.out.println("deleting");
        HttpResponse httpResponse = firstUserHelper.deleteAccount(secondUserToken);
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        HttpResponse httpResponse1 = secondUserHelper.deleteAccount(firstUserToken);
        assertThat(httpResponse1.getStatusLine().getStatusCode()).isEqualTo(200);
    }

}
