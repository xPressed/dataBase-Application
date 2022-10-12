package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Shift;
import ru.xpressed.databaseapplication.repository.ShiftRepository;;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ShiftController {
    private ShiftRepository shiftRepository;

    @Autowired
    public void setShiftRepository(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/shift")
    public String show(Model model, HttpServletRequest request,
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
        model.addAttribute("linkInOrAccount", "/shift?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", shiftRepository.findAll());
        return "/shift/shift";
    }

    @GetMapping("/shift/update")
    public String getUpdate(Model model, @RequestParam("id") int id) {
        model.addAttribute("shift", shiftRepository.find(id));
        return "/shift/update";
    }

    @PostMapping("/shift/update")
    public String postUpdate(@Valid Shift shift, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shift", shift);
            return "/shift/update";
        }
        try {
            shiftRepository.delete(id);
            shiftRepository.insert(shift.getType(), shift.getTimetable());
            model.addAttribute("message", "Shift Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/shift/update";
    }

    @GetMapping("/shift/add")
    public String getAdd(Model model) {
        model.addAttribute("shift", new Shift());
        return "/shift/add";
    }

    @PostMapping("/shift/add")
    public String postAdd(@Valid Shift shift, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("shift", shift);
            return "/shift/add";
        }
        try {
            shiftRepository.insert(shift.getType(), shift.getTimetable());
            model.addAttribute("message", "New Shift Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/shift/add";
    }

    @GetMapping("/shift/delete")
    public String delete(@RequestParam("id") int id) {
        try {
            shiftRepository.delete(id);
        } catch (Exception e) {
            return "redirect:/shift?depend";
        }
        return "redirect:/shift";
    }
}
