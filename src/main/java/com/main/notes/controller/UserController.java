package com.main.notes.controller;

import com.main.notes.event.*;
import com.main.notes.service.IMessageService;
import com.main.notes.dto.UserDto;
import com.main.notes.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final IMessageService messageService;
    private final HttpServletRequest session;

    @Autowired
    public UserController(IUserService userService,
                          IMessageService messageService,
                          HttpServletRequest session)
    {
        this.userService = userService;
        this.messageService = messageService;
        this.session = session;
    }

    @PostMapping("/create")
    public RedirectView CreateUser(@Valid UserDto user, BindingResult result)
    {
        if (result.hasErrors()) {
            return new RedirectView("/notes/register?error");
        }

        if (!ValidateUsername(user.getUsername()))
        {
            return new RedirectView("/notes/register?invalidUsername");
        }

        if (!ValidatePassword(user.getPassword()))
        {
            return new RedirectView("/notes/register?invalidPassword");
        }

        if (!user.getPassword().equals(user.getRepeatingPassword()))
        {
            return new RedirectView("/notes/register?passwordMismatch");
        }

        IUserEvent userResult = userService.CreateUser(user);

        if (userResult instanceof UserCreatedEvent)
        {
            return new RedirectView("/notes/login?registerSuccess");
        }
        return new RedirectView("/notes/error");
    }

    @PostMapping("/delete")
    public RedirectView DeleteUser(Principal principal)
    {
        String username = principal.getName();

        IUserEvent userResult = userService.DeleteUser(username);
        IMessageEvent messageResult = messageService.deleteMessageByUsername(username);
        try
        {
            session.logout();
        }
        catch(Exception ex)
        {
            return new RedirectView("/notes/error");
        }

        if (userResult instanceof UserDeletedEvent
                && messageResult instanceof MessageDeletedEvent)
        {
            return new RedirectView("/notes/login?userDeleted");
        }
        return new RedirectView("/notes/error");
    }

    @PostMapping("/change-password")
    public RedirectView ChangePassword(@Valid UserDto user, BindingResult result, Principal principal)
    {
        if (!user.getUsername().equals(principal.getName()))
        {
            return new RedirectView("/notes/error");
        }

        if (result.hasErrors()) {
            return new RedirectView("/notes/change-password?error");
        }

        if (!ValidatePassword(user.getPassword()))
        {
            return new RedirectView("/notes/change-password?invalidPassword");
        }

        if (!user.getPassword().equals(user.getRepeatingPassword()))
        {
            return new RedirectView("/notes/change-password?passwordMismatch");
        }

        IUserEvent userResult = userService.ChangePassword(user);

        if (userResult instanceof UserUpdatedEvent)
        {
            return new RedirectView("/notes/change-password?changeSuccess");
        }
        return new RedirectView("/notes/error");
    }

    private boolean ValidatePassword(String password)
    {
        return !(password.length() > 250
                || password.length() < 8
                || password.contains(" "));
    }

    private boolean ValidateUsername(String username)
    {
        return !(username.contains(" ")
                || username.length() > 45
                || username.length() < 5);
    }
}
