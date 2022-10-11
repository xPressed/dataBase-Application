package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.xpressed.databaseapplication.entity.User;
import ru.xpressed.databaseapplication.repository.UserRepository;
import ru.xpressed.databaseapplication.security.SecurityConfiguration;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String completeRegistration(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "registration";
        }

        if (user.getRepeated().isEmpty()) {
            model.addAttribute("reperr", "Repeated password can not be empty!");
            return "registration";
        }

        if (!user.getPassword().equals(user.getRepeated())) {
            model.addAttribute("reperr", "Passwords don't match!");
            return "registration";
        }

        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(securityConfiguration.encoder().encode(user.getPassword()));
            user.setEnabled(false);
            userRepository.save(user);
            model.addAttribute("message", "Registration Completed");
            model.addAttribute("onload", "redirectTimer()");
        } else {
            bindingResult.rejectValue("username", "user.username", "This username is already taken!");
        }
        return "registration";
    }
}
