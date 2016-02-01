package com.stormpath.tutorial.messages;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MessageServiceTest {


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
}