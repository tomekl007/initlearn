package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.controller.jsonrequest.MessageData;
import com.stormpath.tutorial.controller.jsonrequest.MessageOverview;
import com.stormpath.tutorial.messages.Message;
import com.stormpath.tutorial.messages.MessageService;
import com.stormpath.tutorial.user.UserService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;


@Controller
public class MessagesController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    ResponseEntity msg(ServletRequest servletRequest, @RequestBody MessageData messageData) {
        List<Account> receiver = userService.findAccountsByEmail(messageData.emailTo);
        if (receiver.size() == 0) {
            return new ResponseEntity<>("there is no user with emailTo : " + messageData.emailTo,
                    HttpStatus.BAD_REQUEST);
        } else {
            return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(servletRequest,
                    a -> {
                        if (messageToYourself(receiver, a)) {
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        }
                        messageService.sendMessageToUser(receiver.get(0), messageData.text, a);
                        return new ResponseEntity<>(
                                messageService.retrieveAllMessagesInConversationWith(a, messageData.emailTo),
                                HttpStatus.OK);
                    });
        }
    }

    private boolean messageToYourself(List<Account> receiver, Account a) {
        return a.getEmail().equals(receiver.get(0).getEmail());
    }

    @RequestMapping(value = "/msg/{email:.+}", method = RequestMethod.GET)
    ResponseEntity<List<Message>>
    retrieveAllMessagesForLoggedUserForConversationWith(ServletRequest servletRequest,
                                                        @PathVariable("email") String email,
                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(
                servletRequest, a -> messageService.retrieveAllMessagesInConversationWith(a, email, page, size));
    }

    @RequestMapping(value = "/msg/{email:.+}/read", method = RequestMethod.POST)
    ResponseEntity markAllMessagesAsRead(ServletRequest servletRequest,
                                         @PathVariable("email") String email) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(
                servletRequest, a -> new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

    }

    @RequestMapping(value = "/msg/{email:.+}/notread", method = RequestMethod.GET)
    ResponseEntity<List<Message>> getAllNotReadMessages(ServletRequest servletRequest,
                                                        @PathVariable("email") String email) {
        return AccountUtils.actionResponseEntityForAuthenticatedUserOrUnauthorized(
                servletRequest, a -> new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

    }

    @RequestMapping(value = "/msg/overview", method = RequestMethod.GET)
    ResponseEntity<List<MessageOverview>> getMessagesOverview(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(
                servletRequest, a -> messageService.getMessagesOverview(a));
    }
}
