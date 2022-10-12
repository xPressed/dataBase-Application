package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Menu;
import ru.xpressed.databaseapplication.repository.MenuRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MenuController {
    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/menu")
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
        model.addAttribute("linkInOrAccount", "/menu?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", menuRepository.findAll());
        return "/menu/menu";
    }

    @GetMapping("/menu/update")
    public String getUpdate(Model model, @RequestParam("id") int id) {
        model.addAttribute("menu", menuRepository.find(id));
        return "/menu/update";
    }

    @PostMapping("/menu/update")
    public String postUpdate(@Valid Menu menu, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("menu", menu);
            return "/menu/update";
        }
        try {
            menuRepository.delete(id);
            menuRepository.insert(menu.getType());
            model.addAttribute("message", "Menu Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/menu/update";
    }

    @GetMapping("/menu/add")
    public String getAdd(Model model) {
        model.addAttribute("menu", new Menu());
        return "/menu/add";
    }

    @PostMapping("/menu/add")
    public String postAdd(@Valid Menu menu, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("menu", menu);
            return "/menu/add";
        }
        try {
            menuRepository.insert(menu.getType());
            model.addAttribute("message", "New Menu Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/menu/add";
    }

    @GetMapping("/menu/delete")
    public String delete(@RequestParam("id") int id) {
        try {
            menuRepository.delete(id);
        } catch (Exception e) {
            return "redirect:/menu?depend";
        }
        return "redirect:/menu";
    }
}
