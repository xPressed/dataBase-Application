package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Waiter;
import ru.xpressed.databaseapplication.repository.WaiterRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class WaiterController {
    private WaiterRepository waiterRepository;

    @Autowired
    public void setWaiterRepository(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @GetMapping("/waiter")
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
        model.addAttribute("linkInOrAccount", "/waiter?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", waiterRepository.findAll());
        return "/waiter/waiter";
    }

    @GetMapping("/waiter/update")
    public String showClientUpdatePage(Model model, @RequestParam("id") int id) {
        model.addAttribute("waiter", waiterRepository.find(id));
        return "/waiter/update";
    }

    @PostMapping("/waiter/update")
    public String completeClientUpdate(@Valid Waiter waiter, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("waiter", waiter);
            return "/waiter/update";
        }
        try {
            waiterRepository.delete(id);
            waiterRepository.insert(waiter.getSurname(), waiter.getName(), waiter.getPatronymic(), waiter.getGender(), waiter.getDateOfBirth(), waiter.getID_Shift());
            model.addAttribute("message", "Waiter Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/waiter/update";
    }

    @GetMapping("/waiter/add")
    public String showClientAddPage(Model model) {
        model.addAttribute("waiter", new Waiter());
        return "/waiter/add";
    }

    @PostMapping("/waiter/add")
    public String completeClientAdd(@Valid Waiter waiter, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("waiter", waiter);
            return "/waiter/add";
        }
        try {
            waiterRepository.insert(waiter.getSurname(), waiter.getName(), waiter.getPatronymic(), waiter.getGender(), waiter.getDateOfBirth(), waiter.getID_Shift());
            model.addAttribute("message", "New Waiter Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/waiter/add";
    }

    @GetMapping("/waiter/delete")
    public String deleteClient(@RequestParam("id") int id) {
        try {
            waiterRepository.delete(id);
        } catch (Exception e) {
            return "redirect:/waiter?depend";
        }
        return "redirect:/waiter";
    }
}
