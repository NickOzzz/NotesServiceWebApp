package com.main.notes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DefaultRedirectController {
    @GetMapping("/notes")
    public RedirectView RedirectToMainNotesPage()
    {
        return new RedirectView("/notes/login");
    }

    @GetMapping
    public RedirectView RedirectToMainProfilePage()
    {
        return new RedirectView("/about/nikita-lavrenchuk");
    }
}
