package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.xpressed.databaseapplication.entity.User;
import ru.xpressed.databaseapplication.repository.UserRepository;
import ru.xpressed.databaseapplication.security.SecurityConfiguration;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteController {
    private SecurityConfiguration securityConfiguration;
    private UserRepository userRepository;

    @Autowired
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/delete")
    public String showUpdateForm(HttpServletRequest request, Model model) {
        model.addAttribute("welcome", "Delete \"" + request.getUserPrincipal().getName() + "\"?");
        model.addAttribute("user", new User());
        return "delete";
    }

    @PostMapping("/delete")
    public String completeUpdate(User user, Model model, HttpServletRequest request) {
        model.addAttribute("welcome", "Delete \"" + request.getUserPrincipal().getName() + "\"?");

        if (!securityConfiguration.encoder().matches(user.getOld(), userRepository.findByUsername(request.getUserPrincipal().getName()).getPassword())) {
            model.addAttribute("olderr", "Old password is incorrect!");
            return "delete";
        } else {
            userRepository.delete(userRepository.findByUsername((request.getUserPrincipal().getName())));
            model.addAttribute("onload", "redirectLogout()");
        }

        return "delete";
    }

}
