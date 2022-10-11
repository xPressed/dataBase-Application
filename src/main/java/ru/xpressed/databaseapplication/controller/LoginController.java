package ru.xpressed.databaseapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam("complete") Optional<String> completed) {
        if (completed.isPresent()) {
            model.addAttribute("onload", "parent.closeIFrame()");
        }
        return "login";
    }
}
