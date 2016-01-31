package com.stormpath.tutorial.messages;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.tutorial.controller.jsonrequest.MessageOverview;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageService {

    @Autowired
    UserService userService;
    public static final String MESSAGES_FIELD = "messages";
    public static final String MESSAGES_READ_OFFSET_FIELD = "messages-offset";
    public static final String CONVERSATIONS_WITH_FIELD = "conversations-with";
    public static final String LAST_MESSAGE_FIELD = "last-message";

    @Autowired
    MessagesRepository messagesRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(Account receiver, String text, Account sender) {

        logger.info("send message from " + sender.getEmail() + " to " + receiver.getEmail());

        addMessageToConversation(text, sender, receiver);
        return "OK";
    }

    public void addMessageToConversation(String text, Account sender, Account receiver) {
        AccountUtils.addCustomListFieldToAccount(sender,
                CONVERSATIONS_WITH_FIELD, Collections.singletonList(receiver.getEmail()), sender.getCustomData());
        AccountUtils.addCustomListFieldToAccount(receiver,
                CONVERSATIONS_WITH_FIELD, Collections.singletonList(sender.getEmail()), receiver.getCustomData());
        DateTime dateTime = new DateTime();
        Message message = new Message(text, dateTime.getMillis(),
                sender.getEmail(), receiver.getEmail());

        addMessageToDb(new MessageDb(text, dateTime.toDate(), sender.getEmail(), receiver.getEmail()));

        addLastMessage(message, sender, receiver.getEmail());
        addLastMessage(message, receiver, sender.getEmail());

        addMessageToCustomData(message, sender, receiver);
        addMessageToCustomData(message, receiver, sender);
    }

    private void addMessageToDb(MessageDb messageDb) {
        messagesRepository.save(messageDb);
    }

    public List<String> getConversationWithField(Account account) {
        return AccountUtils.getCustomListFieldValue(account, CONVERSATIONS_WITH_FIELD);
    }

    public static void addMessageToCustomData(Message message, Account sender, Account receiver) {
        CustomData customData = sender.getCustomData();
        String messageField = getMessageField(receiver.getEmail());
        String messageOffsetField = getMessageOffsetField(receiver.getEmail());
        logger.info("sender : " + sender + ", receiver: " + receiver + " add message for field : " + messageField);

        List<Message> messagesList;
        Object messages = customData.get(messageField);
        if (messages == null) {
            customData.put(messageOffsetField, 0);
            messagesList = new LinkedList<>();
        } else {
            messagesList = (List<Message>) messages;
        }
        messagesList.add(message);

        customData.put(messageField, messagesList);
        sender.save();
    }

    private static String getMessageOffsetField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing
        return MESSAGES_READ_OFFSET_FIELD + "-" + encode;
    }

    public static String getMessageField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing

        return MESSAGES_FIELD + "-" + encode;
    }

    public static String getLastMessageField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing

        return LAST_MESSAGE_FIELD + "-" + encode;
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

    public Integer markAllMessagesInConversationAsRead(Account account, String conversationEmail) {
        List<Message> messages = retrieveAllMessagesInConversationWith(account, conversationEmail);
        String messageOffsetField = getMessageOffsetField(conversationEmail);
        int newOffset = markNewReadOffset(messages);
        account.getCustomData().put(messageOffsetField, newOffset);
        account.save();
        return newOffset;
    }

    public static Integer markNewReadOffset(List messages) {
        return messages.size();
    }


    public List<Message> getAllNotReadMessages(Account account, String conversationEmail) {
        List<Message> messages = retrieveAllMessagesInConversationWith(account, conversationEmail);
        Integer readOffset = AccountUtils.getCustomIntegerValue(account, getMessageOffsetField(conversationEmail));
        return getNotReadMessages(messages, readOffset);
    }

    public static List<Message> getNotReadMessages(List<Message> messages, Integer readOffset) {
        return messages.subList(readOffset, messages.size());
    }

    public static void addLastMessage(Message message, Account account, String email) {
        LinkedHashMap linkedHashMap = Message.toLinkedHashMap(message);
        account.getCustomData().put(getLastMessageField(email), Collections.singletonList(linkedHashMap));
        account.save();
    }

    public MessageOverview getLastMessage(Account account, String email) {
        Object o = account.getCustomData().get(getLastMessageField(email));
        String userFullName = userService.findUserByEmail(email).map(u -> u.fullName).orElse("");

        if (o == null) {
            return new MessageOverview(email, userFullName, null);
        } else {
            List<LinkedHashMap> messages = (List<LinkedHashMap>) o;
            return new MessageOverview(email, userFullName, Message.mapFromLinkedHashMap(messages.get(0)));
        }
    }

    public List<MessageOverview> getMessagesOverview(Account a) {
        return getConversationWithField(a)
                .stream()
                .map(email -> getLastMessage(a, email))
                .filter(m -> m.lastMessage != null)
                .collect(Collectors.toList());
    }
}