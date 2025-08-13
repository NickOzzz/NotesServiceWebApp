package com.main.notes.controller;

import com.main.notes.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
    @GetMapping("/notes/register")
    public String Register(Model model)
    {
        model.addAttribute("UserDto", new UserDto());
        return "register";
    }
}
