package com.stormpath.tutorial.controller;

import com.stormpath.tutorial.messages.Message;
import com.stormpath.tutorial.messages.MessageService;
import com.stormpath.tutorial.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.util.List;


@Controller
public class MessagesController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    MessageService messageService;


    @RequestMapping("/msg")//todo should be put
    ResponseEntity msg(ServletRequest servletRequest, @RequestParam("text") String text, @RequestParam("to") String emailTo) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest,
                a -> messageService.sendMessageToUser(emailTo, text, a));
    }

    @RequestMapping("/allMsg")    //should be get
    ResponseEntity<List<Message>> retrieveAllMessagesForLoggedUser(ServletRequest servletRequest) {
        return actionForUserOrNotFound(servletRequest);

    }

    private ResponseEntity<List<Message>> actionForUserOrNotFound(ServletRequest servletRequest) {
        return AccountUtils.actionForAuthenticatedUserOrUnauthorized(servletRequest, messageService::retrieveAllMessages);
    }
}
