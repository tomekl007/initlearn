package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.tutorial.messages.Message;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Controller
public class MessagesController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);
    public static final String MESSAGES_FIELD = "messages";

    @Autowired
    private Client client;


    @RequestMapping("/msg")//todo should be put
    ResponseEntity msg(ServletRequest servletRequest, @RequestParam("text") String text, @RequestParam("to") String emailTo) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            //or Account account = (Account)servletRequest.getAttribute("account");
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            sendMessageToUser(emailTo, text, authenticatedAccount);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping("/allMsg") //should be get
    ResponseEntity<List<Message>> retrieveAllMessagesForLoggedUser(ServletRequest servletRequest){
        return actionForUserOrNotFound(servletRequest);

    }

    private ResponseEntity<List<Message>> actionForUserOrNotFound(ServletRequest servletRequest) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            //or Account account = (Account)servletRequest.getAttribute("account");
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return new ResponseEntity<>(retrieveAllMessages(authenticatedAccount), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    public void sendMessageToUser(String emailTo, String text, Account from) {

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
    }
    
    public List<Message> retrieveAllMessages(Account account){
        Object messages = account.getCustomData().get(MESSAGES_FIELD);
        if(messages == null){
            return Collections.emptyList();
        }else{
            return (List<Message>) messages;
        }

    }

}
