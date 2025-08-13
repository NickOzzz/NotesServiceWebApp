package com.main.notes.controller;

import com.main.notes.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/notes")
public class ChangePasswordController {
    @GetMapping("/change-password")
    public String GetInfo(Model model, Principal user)
    {
        model.addAttribute("UserDto", new UserDto());
        model.addAttribute("username", user.getName());
        return "changePassword";
    }
}
