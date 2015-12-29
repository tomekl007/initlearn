package com.stormpath.tutorial.messages;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.tutorial.user.UserService;
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

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(String emailTo, String text, Account from) {

        logger.info("send message from " + from.getEmail() + " to " + emailTo);

        List<Account> accountsToSendTo = userService.findAccountsByEmail(emailTo);

        Message message = new Message(false, text, from.getEmail(), new DateTime().getMillis());
        
        addMessageForAccounts(message, accountsToSendTo);
        return "OK";
    }

    private void addMessageForAccounts(Message message, List<Account> accountsToSendTo) {
        for (Account account : accountsToSendTo) {
            addMessageToCustomData(message, account);
        }
    }

    private void addMessageToCustomData(Message message, Account account) {
        CustomData customData = account.getCustomData();

        List<Message> messagesList;
        Object messages = customData.get(MESSAGES_FIELD);
        if (messages == null) {
            messagesList = new LinkedList<>();
        } else {
            messagesList = (List<Message>) messages;
        }
        messagesList.add(message);
        customData.put(MESSAGES_FIELD, messagesList);
        account.save();
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
