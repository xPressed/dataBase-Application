package ru.xpressed.databaseapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class TablesController {
    @GetMapping("/tables")
    public String showTables(Model model, HttpServletRequest request,
                             @RequestParam("account") Optional<String> account) {
        if (account.isPresent()) {
            model.addAttribute("blur", "5px");
        } else {
            model.addAttribute("blur", "0");
        }

        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "LogOut");
        model.addAttribute("linkInOrAccount", "/tables?account");
        model.addAttribute("textInOrAccount", "Account");
        return "tables";
    }
}
