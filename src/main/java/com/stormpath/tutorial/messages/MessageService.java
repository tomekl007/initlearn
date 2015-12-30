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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageService {
    public static final String MESSAGES_FIELD = "messages";

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(String emailTo, String text, Account from) {

        logger.info("send message from " + from.getEmail() + " to " + emailTo);

        Account accountsToSendTo = userService.findAccountsByEmail(emailTo).get(0);

        Message message = new Message(false, text, new DateTime().getMillis(),
                from.getEmail(), accountsToSendTo.getEmail());

        addMessageForAccounts(message, from, accountsToSendTo);
        addMessageForAccounts(message, accountsToSendTo, from);
        return "OK";
    }

    private void addMessageForAccounts(Message message, Account accountToSendTo, Account from) {

        addMessageToCustomData(message, accountToSendTo, from);

    }

    private void addMessageToCustomData(Message message, Account account, Account from) {
        CustomData customData = account.getCustomData();
        String messageField = getMessageField(from.getEmail());
        logger.info("Add account for field : " + messageField);

        List<Message> messagesList;
        Object messages = customData.get(messageField);
        if (messages == null) {
            messagesList = new LinkedList<>();
        } else {
            messagesList = (List<Message>) messages;
        }
        messagesList.add(message);

        customData.put(messageField, messagesList);
        account.save();
    }

    private String getMessageField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing

        return MESSAGES_FIELD + "-" + encode;
    }

    private String cleanEmailFromSpecialCharacters(String email) {
        return email
                .replace("@", "_-_-_")
                .replace(".", "_-_-_");
    }

    public List<Message> retrieveAllMessages(Account account) {
        Object messages = account.getCustomData().get(MESSAGES_FIELD);
        if (messages == null) {
            return Collections.emptyList();
        } else {
            return (List<Message>) messages;
        }
    }

    public List<Message> retrieveAllMessagesInConversationWith(Account account, String conversationEmail, Optional<Boolean> markAsRead) {
        Object messages = account.getCustomData().get(getMessageField(conversationEmail));
        if (messages == null) {
            return Collections.emptyList();
        } else {
            return (List<Message>) messages;
        }
    }
}
