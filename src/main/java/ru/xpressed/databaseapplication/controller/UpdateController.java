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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class UpdateController {
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

    @GetMapping("/update")
    public String showUpdateForm(HttpServletRequest request, Model model) {
        model.addAttribute("welcome", "Update \"" + request.getUserPrincipal().getName() + "\"?");
        User user = new User();
        user.setUsername(request.getUserPrincipal().getName());
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")
    public String completeUpdate(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("welcome", "Update \"" + request.getUserPrincipal().getName() + "\"?");

        if (!securityConfiguration.encoder().matches(user.getOld(), userRepository.findByUsername(request.getUserPrincipal().getName()).getPassword())) {
            model.addAttribute("olderr", "Old password is incorrect!");
            return "update";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "update";
        }

        if (!user.getPassword().equals(user.getRepeated())) {
            model.addAttribute("reperr", "Passwords don't match!");
            return "update";
        }

        if ((Objects.equals(user.getUsername(), request.getUserPrincipal().getName())) | (userRepository.findByUsername(user.getUsername()) == null)) {
            userRepository.delete(userRepository.findByUsername((request.getUserPrincipal().getName())));
            user.setPassword(securityConfiguration.encoder().encode(user.getPassword()));
            userRepository.save(user);

            model.addAttribute("onload", "redirectLogout()");
        } else {
            bindingResult.rejectValue("username", "user.username", "This username is already taken!");
        }

        return "update";
    }
}
