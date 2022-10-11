package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xpressed.databaseapplication.entity.Client;
import ru.xpressed.databaseapplication.repository.ClientRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ClientController {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/client")
    public String showClientPage(Model model, HttpServletRequest request,
                                 @RequestParam("account") Optional<String> account,
                                 @RequestParam("update") Optional<Integer> update,
                                 @RequestParam("add") Optional<String> add) {
        if (account.isPresent() | update.isPresent() | add.isPresent()) {
            model.addAttribute("blur", "5px");
        } else {
            model.addAttribute("blur", "0");
        }

        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "LogOut");
        model.addAttribute("linkInOrAccount", "/client?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", clientRepository.findAll());
        return "/client/client";
    }

    @GetMapping("/client/update")
    public String showClientUpdatePage(Model model, @RequestParam("id") int id) {
        model.addAttribute("client", clientRepository.find(id));
        return "/client/update";
    }

    @PostMapping("/client/update")
    public String completeClientUpdate(@Valid Client client, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            return "/client/update";
        }
        try {
            clientRepository.insert(client.getID_Order(), client.getSurname(), client.getName(), client.getPatronymic());
            clientRepository.delete(id);
            model.addAttribute("message", "Client Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/client/update";
    }

    @GetMapping("/client/add")
    public String showClientAddPage(Model model) {
        model.addAttribute("client", new Client());
        return "/client/add";
    }

    @PostMapping("/client/add")
    public String completeClientAdd(@Valid Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("client", client);
            return "/client/add";
        }
        try {
            clientRepository.insert(client.getID_Order(), client.getSurname(), client.getName(), client.getPatronymic());
            model.addAttribute("message", "New Client Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/client/add";
    }

    @GetMapping("/client/delete")
    public String deleteClient(@RequestParam("id") int id) {
        clientRepository.delete(id);
        return "redirect:/client";
    }
}
