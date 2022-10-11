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

        model.addAttribute("rows", distributionRepository.findAll());
        return "/distribution/distribution";
    }

    @GetMapping("/distribution/update")
    public String showClientUpdatePage(Model model, @RequestParam("id") int[] id) {
        model.addAttribute("distribution", distributionRepository.find(id[0], id[1]));
        return "/distribution/update";
    }

    @PostMapping("/distribution/update")
    public String completeClientUpdate(@Valid Distribution distribution, BindingResult bindingResult, Model model, @RequestParam("id") int[] id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("distribution", distribution);
            return "/distribution/update";
        }
        try {
            distributionRepository.insert(distribution.getID_Order(), distribution.getID_Waiter());
            distributionRepository.delete(id[0], id[1]);
            model.addAttribute("message", "Client Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/distribution/update";
    }

    @GetMapping("/distribution/add")
    public String showClientAddPage(Model model) {
        model.addAttribute("distribution", new Distribution());
        return "/distribution/add";
    }

    @PostMapping("/distribution/add")
    public String completeClientAdd(@Valid Distribution distribution, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("distribution", distribution);
            return "/distribution/add";
        }
        try {
            distributionRepository.insert(distribution.getID_Order(), distribution.getID_Waiter());
            model.addAttribute("message", "New Client Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/distribution/add";
    }

    @GetMapping("/distribution/delete")
    public String deleteClient(@RequestParam("id") int[] id) {
        distributionRepository.delete(id[0], id[1]);
        return "redirect:/distribution";
    }
}
