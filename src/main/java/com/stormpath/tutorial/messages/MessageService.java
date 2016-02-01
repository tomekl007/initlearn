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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MessageService {

    @Autowired
    UserService userService;
    public static final String MESSAGES_READ_OFFSET_FIELD = "messages-offset";

    @Autowired
    MessagesRepository messagesRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public String sendMessageToUser(Account receiver, String text, Account sender) {

        logger.info("send message from " + sender.getEmail() + " to " + receiver.getEmail());

        addMessageToConversation(text, sender, receiver);
        return "OK";
    }

    public void addMessageToConversation(String text, Account sender, Account receiver) {
        DateTime dateTime = new DateTime();

        addMessageToDb(new MessageDb(text, dateTime.toDate(), sender.getEmail(), receiver.getEmail()));
    }

    private void addMessageToDb(MessageDb messageDb) {
        messagesRepository.save(messageDb);
    }

    public List<String> getConversationWithField(Account account) {
        String participantEmail = account.getEmail();
        List<MessageDb> allConversationsWith = messagesRepository.getAllConversationsWith(participantEmail);

        return allConversationsWith
                .stream()
                .map(m -> Objects.equals(m.getFrom_email(), participantEmail) ? m.getTo_email() : m.getFrom_email())
                .distinct()
                .collect(Collectors.toList());
    }


    private static String getMessageOffsetField(String email) {
        String encode = cleanEmailFromSpecialCharacters(email); //todo think about better way of replacing
        return MESSAGES_READ_OFFSET_FIELD + "-" + encode;
    }

    private static String cleanEmailFromSpecialCharacters(String email) {
        return email
                .replace("@", "_-_-_")
                .replace(".", "_-_-_");
    }

    public List<Message> retrieveAllMessagesInConversationWith(Account account, String conversationEmail) {
        List<MessageDb> allMessages = messagesRepository.getAllMessagesForConversation(account.getEmail(), conversationEmail);
        return mapFromMessageDb(allMessages);

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

    public MessageOverview getLastMessage(Account account, String email) {
        List<MessageDb> lastMessageInConversation = messagesRepository.getLastMessageForConversations(account.getEmail(),
                email, new PageRequest(0, 1));

        String userFullName = userService.findUserByEmail(email).map(u -> u.fullName).orElse("");

        if (lastMessageInConversation == null || lastMessageInConversation.isEmpty()) {
            return new MessageOverview(email, userFullName, null);
        } else {
            return new MessageOverview(email, userFullName, mapFromMessageDb(lastMessageInConversation).get(0));
        }
    }

    public List<MessageOverview> getMessagesOverview(Account a) {
        return getConversationWithField(a)
                .stream()
                .map(email -> getLastMessage(a, email))
                .filter(m -> m.lastMessage != null)
                .collect(Collectors.toList());
    }

    public List<Message> mapFromMessageDb(List<MessageDb> messageDb) {
        return messageDb.stream()
                .map(m -> new Message(m.text, m.timestamp.getTime(), m.from_email, m.to_email))
                .collect(Collectors.toList());
    }

}