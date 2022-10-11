package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Positions;
import ru.xpressed.databaseapplication.repository.PositionsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PositionsController {
    private PositionsRepository positionsRepository;

    @Autowired
    public void setPositionsRepository(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    @GetMapping("/positions")
    public String showDishPage(Model model, HttpServletRequest request,
                               @RequestParam("account") Optional<String> account,
                               @RequestParam("update") Optional<String> update,
                               @RequestParam("add") Optional<String> add) {
        if (account.isPresent() | update.isPresent() | add.isPresent()) {
            model.addAttribute("blur", "5px");
        } else {
            model.addAttribute("blur", "0");
        }

        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "LogOut");
        model.addAttribute("linkInOrAccount", "/positions?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", positionsRepository.findAll());
        return "/positions/positions";
    }

    @GetMapping("/positions/update")
    public String showClientUpdatePage(Model model, @RequestParam("id") String id) {
        model.addAttribute("positions", positionsRepository.find(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1])).get(0));
        return "/positions/update";
    }

    @PostMapping("/positions/update")
    public String completeClientUpdate(@Valid Positions positions, BindingResult bindingResult, Model model, @RequestParam("id") String id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("positions", positions);
            return "/positions/update";
        }
        try {
            positionsRepository.delete(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1]));
            positionsRepository.insert(positions.getID_Menu(), positions.getID_Dish());
            model.addAttribute("message", "Positions Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/positions/update";
    }

    @GetMapping("/positions/add")
    public String showClientAddPage(Model model) {
        model.addAttribute("positions", new Positions());
        return "/positions/add";
    }

    @PostMapping("/positions/add")
    public String completeClientAdd(@Valid Positions positions, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("positions", positions);
            return "/positions/add";
        }
        try {
            positionsRepository.insert(positions.getID_Menu(), positions.getID_Dish());
            model.addAttribute("message", "New Position Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/positions/add";
    }

    @GetMapping("/positions/delete")
    public String deleteClient(@RequestParam("id") String id) {
        try {
            positionsRepository.delete(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1]));
        } catch (Exception e) {
            return "redirect:/positions?depend";
        }
        return "redirect:/positions";
    }
}
