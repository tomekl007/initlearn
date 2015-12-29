package com.stormpath.tutorial.controller;

import com.stormpath.tutorial.controller.jsonrequest.MessageData;
import com.stormpath.tutorial.messages.Message;
import com.stormpath.tutorial.messages.MessageService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
public class MessagesController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    MessageService messageService;


    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    ResponseEntity msg(ServletRequest servletRequest, @RequestBody MessageData messageData) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> messageService.sendMessageToUser(messageData.emailTo, messageData.text, a));
    }

    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    ResponseEntity<List<Message>> retrieveAllMessagesForLoggedUser(ServletRequest servletRequest) {
        return actionForUserOrNotFound(servletRequest);

    }

    @RequestMapping(value = "/msg/{email:.+}", method = RequestMethod.GET)
    ResponseEntity<List<Message>> retrieveAllMessagesForLoggedUserForConversationWith(ServletRequest servletRequest,
                                                                                      @PathVariable("email") String email,
                                                                                      @RequestParam(value = "read", required = false) Optional<Boolean> read) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(
                servletRequest, a -> messageService.retrieveAllMessagesInConversationWith(a, email, read));
    }

    private ResponseEntity<List<Message>> actionForUserOrNotFound(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, messageService::retrieveAllMessages);
    }
}
