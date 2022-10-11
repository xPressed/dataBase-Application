package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Dish;
import ru.xpressed.databaseapplication.repository.DishRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class DishController {
    private DishRepository dishRepository;

    @Autowired
    public void setDishRepository(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping("/dish")
    public String showDishPage(Model model, HttpServletRequest request,
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

        model.addAttribute("rows", dishRepository.findAll());
        return "/dish/dish";
    }

    @GetMapping("/dish/update")
    public String showClientUpdatePage(Model model, @RequestParam("id") int id) {
        model.addAttribute("dish", dishRepository.find(id));
        return "/dish/update";
    }

    @PostMapping("/dish/update")
    public String completeClientUpdate(@Valid Dish dish, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dish", dish);
            return "/dish/update";
        }
        try {
            dishRepository.insert(dish.getType(), dish.getCalories(), dish.getCompound(), dish.getPrice());
            dishRepository.delete(id);
            model.addAttribute("message", "Client Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/dish/update";
    }

    @GetMapping("/dish/add")
    public String showClientAddPage(Model model) {
        model.addAttribute("dish", new Dish());
        return "/dish/add";
    }

    @PostMapping("/dish/add")
    public String completeClientAdd(@Valid Dish dish, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("dish", dish);
            return "/dish/add";
        }
        try {
            dishRepository.insert(dish.getType(), dish.getCalories(), dish.getCompound(), dish.getPrice());
            model.addAttribute("message", "New Client Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/dish/add";
    }

    @GetMapping("/dish/delete")
    public String deleteClient(@RequestParam("id") int id) {
        dishRepository.delete(id);
        return "redirect:/dish";
    }
}
