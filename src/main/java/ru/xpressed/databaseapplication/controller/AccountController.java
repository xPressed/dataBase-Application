package ru.xpressed.databaseapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    @GetMapping("/account")
    public String showAccountPage(HttpServletRequest request, Model model) {
        model.addAttribute("username", "Welcome, " + request.getUserPrincipal().getName() + "!");
        return "account";
    }
}
