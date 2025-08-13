package com.main.notes.controller;

import com.main.notes.dto.GetAllMessagesDto;
import com.main.notes.dto.MessageFiltersDto;
import com.main.notes.service.IMessageService;
import com.main.notes.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/notes/panel")
public class MessagePageController {

    private final IMessageService messageService;

    @Autowired
    public MessagePageController(IMessageService messageService)
    {
        this.messageService = messageService;
    }

    @GetMapping
    public String OpenPanel(Model model, Principal user, @RequestParam(value = "filter", required = false) String filter)
    {
        MessageFiltersDto filters = new MessageFiltersDto()
                .setUsername(user.getName())
                .setReceiverUsername(filter);

        GetAllMessagesDto messages = messageService.getAllMessages(filters);

        model.addAttribute("messages", messages.getMessages());
        model.addAttribute("creators", messages.getCreators());
        model.addAttribute("messageDto", new MessageDto());
        model.addAttribute("username", user.getName());
        model.addAttribute("filter", filter);

        return "getMessage";
    }
}
