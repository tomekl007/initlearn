package com.stormpath.tutorial.messages;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class MessageService {
    public static final String MESSAGES_FIELD = "messages";

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(Account receiver, String text, Account sender) {

        logger.info("send message from " + sender.getEmail() + " to " + receiver.getEmail());

        addMessageToConversation(text, sender, receiver);
        return "OK";
    }

    public static void addMessageToConversation(String text, Account sender, Account receiver) {
        Message message = new Message(false, text, new DateTime().getMillis(),
                sender.getEmail(), receiver.getEmail());

        addMessageToCustomData(message, sender, receiver);
        addMessageToCustomData(message, receiver, sender);
    }

    public static void addMessageToCustomData(Message message, Account sender, Account receiver) {
        CustomData customData = sender.getCustomData();
        String messageField = getMessageField(receiver.getEmail());
        logger.info("sender : " + sender + ", receiver: " + receiver + " add message for field : " + messageField);

        List<Message> messagesList;
        Object messages = customData.get(messageField);
        if (messages == null) {
            messagesList = new LinkedList<>();
        } else {
            messagesList = (List<Message>) messages;
        }
        messagesList.add(message);

        customData.put(messageField, messagesList);
        sender.save();
    }

    public static String getMessageField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing

        return MESSAGES_FIELD + "-" + encode;
    }

    private static String cleanEmailFromSpecialCharacters(String email) {
        return email
                .replace("@", "_-_-_")
                .replace(".", "_-_-_");
    }

    public List<Message> retrieveAllMessagesInConversationWith(Account account, String conversationEmail) {
        Object messages = account.getCustomData().get(getMessageField(conversationEmail));
        if (messages == null) {
            return Collections.emptyList();
        } else {
            return (List<Message>) messages;
        }
    }

    public List<Message> markAllMessagesInConversationAsRead(Account account, String conversationEmail) {
        List<Message> messages = retrieveAllMessagesInConversationWith(account, conversationEmail);
        List<Message> messagesMarkedAsRead = markMessagesAsRead(messages);
        account.getCustomData().put(getMessageField(conversationEmail), messagesMarkedAsRead);
        account.save();
        return messagesMarkedAsRead;
    }

    public static List<Message> markMessagesAsRead(List messages) {
        List<Message> res = new LinkedList<>();

        logger.info("messages.size : " + messages.size());
        logger.info("messages.class : " + messages.getClass());
        for (int i = 0; i < messages.size(); i++) {
            Message m = (Message) messages.get(i);
            logger.info("message: " + m);
            res.add(new Message(true, m.text, m.timestamp, m.fromEmail, m.toEmail));
        }

        return res;

    }
}