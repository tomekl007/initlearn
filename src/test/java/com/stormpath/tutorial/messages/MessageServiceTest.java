package com.stormpath.tutorial.messages;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class MessageServiceTest {

    @Test
    public void shouldAddMessageToCustomData() {
        //given
        CustomData fromCustomData = Mockito.mock(CustomData.class);
        CustomData toCustomData = Mockito.mock(CustomData.class);
        String fromEmail = "from@gmail.com";
        String toEmail = "to@gmail.com";
        Account from = createAccount(fromEmail, fromCustomData);
        Account to = createAccount(toEmail, toCustomData);

        Message message = new Message("txt", DateTime.now().getMillis(), fromEmail, toEmail);

        //when
        MessageService.addMessageToCustomData(message, from, to);

        //then
        Mockito.verify(fromCustomData).put(eq(MessageService.getMessageField(toEmail)), any(Message.class));

    }

    @Test
    public void shouldAddMessageToConversation() {
        //given
        CustomData fromCustomData = Mockito.mock(CustomData.class);
        CustomData toCustomData = Mockito.mock(CustomData.class);
        String fromEmail = "from@gmail.com";
        String toEmail = "to@gmail.com";
        Account from = createAccount(fromEmail, fromCustomData);
        Account to = createAccount(toEmail, toCustomData);

        //when
        MessageService.addMessageToConversation("txt", from, to);

        //then
        Mockito.verify(fromCustomData).put(eq(MessageService.getMessageField(toEmail)), any(Message.class));
        Mockito.verify(toCustomData).put(eq(MessageService.getMessageField(fromEmail)), any(Message.class));

    }

    @Test
    public void shouldAddMessageToConversationThatHasAlreadyOneMessage() {
        //given
        CustomData fromCustomData = Mockito.mock(CustomData.class);
        CustomData toCustomData = Mockito.mock(CustomData.class);
        String fromEmail = "from@gmail.com";
        String toEmail = "to@gmail.com";
        Account from = createAccountWithOneMessage(fromEmail, fromCustomData, toEmail);
        Account to = createAccountWithOneMessage(toEmail, toCustomData, fromEmail);

        //when
        MessageService.addMessageToConversation("txt", from, to);

        //then
        Mockito.verify(fromCustomData).put(eq(MessageService.getMessageField(toEmail)), any(Message.class));
        Mockito.verify(toCustomData).put(eq(MessageService.getMessageField(fromEmail)), any(Message.class));

        assertThat(((List<Message>) fromCustomData.get(MessageService.getMessageField(toEmail))).size()).isEqualTo(2);
        assertThat(((List<Message>) toCustomData.get(MessageService.getMessageField(fromEmail))).size()).isEqualTo(2);

    }

    @Test
    public void shouldMarkAllMessagesAsRead() {
        //given
        List<Message> messages = Arrays.asList(new Message("txt", DateTime.now().getMillis(), "to", "from"));

        //when
        int offset = MessageService.markNewReadOffset(messages);

        //then
        assertThat(offset).isEqualTo(1);

    }


    @Test
    public void shouldGetNotReadMessagesEmpty() {
        //given
        List<Message> messages = Arrays.asList(new Message("txt", DateTime.now().getMillis(), "to", "from"));

        //when
        List<Message> notReadMessages = MessageService.getNotReadMessages(messages, 1);

        //then
        assertThat(notReadMessages.isEmpty()).isEqualTo(true);

    }

    @Test
    public void shouldGetNotReadMessagesNotEmpty() {
        //given
        List<Message> messages = Arrays.asList(new Message("txt", DateTime.now().getMillis(), "to", "from"),
                new Message("txt", DateTime.now().getMillis(), "to", "from"),
                new Message("txt", DateTime.now().getMillis(), "to", "from")
        );

        //when
        List<Message> notReadMessages = MessageService.getNotReadMessages(messages, 1);

        //then
        assertThat(notReadMessages.size()).isEqualTo(2);

    }

    @Test
    public void shouldGetNotReadMessagesEmptyWhenMessagesEmpty() {
        //when
        List<Message> notReadMessages = MessageService.getNotReadMessages(Collections.emptyList(), 0);

        //then
        assertThat(notReadMessages.size()).isEqualTo(0);

    }

    private Account createAccount(String email, CustomData customData) {
        Account from = Mockito.mock(Account.class);
        Mockito.when(from.getEmail()).thenReturn(email);
        Mockito.when(from.getCustomData()).thenReturn(customData);
        return from;
    }


    private Account createAccountWithOneMessage(String fromEmail, CustomData customData, String toEmail) {
        List<Message> messages = new LinkedList<>();
        messages.add(new Message("oldText", DateTime.now().getMillis(), fromEmail, toEmail));
        Mockito.when(customData.get(MessageService.getMessageField(toEmail))).thenReturn(messages);
        return createAccount(fromEmail, customData);
    }

}