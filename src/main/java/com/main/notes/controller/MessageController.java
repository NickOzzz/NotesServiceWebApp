package com.main.notes.controller;

import com.main.notes.service.IMessageService;
import com.main.notes.dto.MessageDto;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    @Value("${message.length.limit}")
    private int MessageLengthLimit;

    @Autowired
    public MessageController(IMessageService messageService)
    {
        this.messageService = messageService;
    }

    @PostMapping("/panel/create")
    public RedirectView generateMessageFromPanel(@Valid MessageDto message, @RequestParam String filter, BindingResult result, Principal user)
    {
        String messageText = message.getMessage();
        if (messageText.isEmpty() || messageText.isBlank()) {
            return new RedirectView("/notes/panel?emptyMessage&filter=" + filter);
        }

        if (messageText.length() > MessageLengthLimit)
        {
            return new RedirectView("/notes/panel?lengthLimit&filter=" + filter);
        }

        message.setCreator(user.getName());
        generateMessage(message);
        return new RedirectView("/notes/panel?filter=" + filter);
    }

    @PostMapping("/panel/update")
    public RedirectView updateMessageFromPanel(@Valid MessageDto message, @RequestParam String filter, BindingResult result, Principal user)
    {
        String messageText = message.getMessage();
        if (messageText.isEmpty() || messageText.isBlank()) {
            return new RedirectView("/notes/panel?emptyMessage&filter=" + filter);
        }

        if (messageText.length() > MessageLengthLimit)
        {
            return new RedirectView("/notes/panel?lengthLimit&filter=" + filter);
        }

        message.setCreator(user.getName());
        updateMessage(message);
        return new RedirectView("/notes/panel?filter=" + filter);
    }

    @PostMapping("/panel/delete")
    public RedirectView deleteMessageFromPanel(@RequestParam String messageId, @RequestParam String filter, Principal user)
    {
        deleteMessage(messageId, user.getName());
        return new RedirectView("/notes/panel?filter=" + filter);
    }

    @PostMapping("/panel/filter")
    public RedirectView filterMessagesFromPanel(@RequestParam String filter)
    {
        return new RedirectView("/notes/panel?filter=" + filter);
    }

    public void generateMessage(@RequestBody MessageDto message)
    {
        message.setTime(new Date(System.currentTimeMillis()).toString());
        messageService.createMessage(message);
    }

    public void updateMessage(MessageDto message)
    {
        message.setTime(new Date(System.currentTimeMillis()).toString());
        messageService.updateMessage(message);
    }

    public void deleteMessage(@PathVariable String messageId, String username)
    {
        messageService.deleteMessage(messageId, username);
    }
}
