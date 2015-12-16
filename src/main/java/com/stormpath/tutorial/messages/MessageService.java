package com.stormpath.tutorial.messages;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.CustomData;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class MessageService {
    public static final String MESSAGES_FIELD = "messages";

    @Autowired
    Client client;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(String emailTo, String text, Account from) {

        logger.info("send message from " + from.getEmail() + " to " + emailTo);

        Stream<Account> accounts = StreamSupport.stream(Spliterators
                .spliteratorUnknownSize(client.getAccounts().iterator(), Spliterator.ORDERED), false);
        Optional<Account> account = accounts.filter(a -> a.getEmail().equals(emailTo)).findFirst();
        CustomData customData = account.get().getCustomData();

        List<Message> messagesList;
        Object messages = customData.get(MESSAGES_FIELD);
        if (messages == null) {
            messagesList = new LinkedList<>();
        } else {
            messagesList = (List<Message>) messages;
        }
        messagesList.add(new Message(false, text, from.getEmail(), new DateTime().getMillis()));
        //todo add sended emails to fromEmail user
        customData.put(MESSAGES_FIELD, messagesList);
        account.get().save();
        return "OK";
    }

    public List<Message> retrieveAllMessages(Account account) {
        Object messages = account.getCustomData().get(MESSAGES_FIELD);
        if (messages == null) {
            return Collections.emptyList();
        } else {
            return (List<Message>) messages;
        }
    }
}
