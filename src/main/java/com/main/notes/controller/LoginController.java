package com.main.notes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/notes")
public class LoginController {
    @GetMapping("/login")
    public String Login()
    {
        return "login";
    }
}
