package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Distribution;
import ru.xpressed.databaseapplication.repository.DistributionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class DistributionController {
    private DistributionRepository distributionRepository;

    @Autowired
    public void setDistributionRepository(DistributionRepository distributionRepository) {
        this.distributionRepository = distributionRepository;
    }

    @GetMapping("/distribution")
    public String show(Model model, HttpServletRequest request,
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
        model.addAttribute("linkInOrAccount", "/distribution?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", distributionRepository.findAll());
        return "/distribution/distribution";
    }

    @GetMapping("/distribution/update")
    public String getUpdate(Model model, @RequestParam("id") String id) {
        model.addAttribute("distribution", distributionRepository.find(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1])).get(0));
        return "/distribution/update";
    }

    @PostMapping("/distribution/update")
    public String postUpdate(@Valid Distribution distribution, BindingResult bindingResult, Model model, @RequestParam("id") String id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("distribution", distribution);
            return "/distribution/update";
        }
        try {
            distributionRepository.delete(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1]));
            distributionRepository.insert(distribution.getID_Order(), distribution.getID_Waiter());
            model.addAttribute("message", "Client Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/distribution/update";
    }

    @GetMapping("/distribution/add")
    public String getAdd(Model model) {
        model.addAttribute("distribution", new Distribution());
        return "/distribution/add";
    }

    @PostMapping("/distribution/add")
    public String postAdd(@Valid Distribution distribution, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("distribution", distribution);
            return "/distribution/add";
        }
        try {
            distributionRepository.insert(distribution.getID_Order(), distribution.getID_Waiter());
            model.addAttribute("message", "New Distribution Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/distribution/add";
    }

    @GetMapping("/distribution/delete")
    public String delete(@RequestParam("id") String id) {
        try {
            distributionRepository.delete(Integer.parseInt(id.split(",")[0]), Integer.parseInt(id.split(",")[1]));
        } catch (Exception e) {
            return "redirect:/distribution?depend";
        }
        return "redirect:/distribution";
    }
}
