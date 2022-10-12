package ru.xpressed.databaseapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xpressed.databaseapplication.entity.Order;
import ru.xpressed.databaseapplication.repository.OrderRepository;;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class OrderController {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order")
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
        model.addAttribute("linkInOrAccount", "/order?account");
        model.addAttribute("textInOrAccount", "Account");

        model.addAttribute("rows", orderRepository.findAll());
        return "/order/order";
    }

    @GetMapping("/order/update")
    public String getUpdate(Model model, @RequestParam("id") int id) {
        model.addAttribute("order", orderRepository.find(id));
        return "/order/update";
    }

    @PostMapping("/order/update")
    public String postUpdate(@Valid Order order, BindingResult bindingResult, Model model, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("order", order);
            return "/order/update";
        }
        try {
            orderRepository.delete(id);
            orderRepository.insert(order.getID_Dish());
            model.addAttribute("message", "Order Updated!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/order/update";
    }

    @GetMapping("/order/add")
    public String getAdd(Model model) {
        model.addAttribute("order", new Order());
        return "/order/add";
    }

    @PostMapping("/order/add")
    public String postAdd(@Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("order", order);
            return "/order/add";
        }
        try {
            orderRepository.insert(order.getID_Dish());
            model.addAttribute("message", "New Order Added!");
        } catch (Exception e) {
            model.addAttribute("message2", "Something went Wrong!");
        }
        return "/order/add";
    }

    @GetMapping("/order/delete")
    public String delete(@RequestParam("id") int id) {
        try {
            orderRepository.delete(id);
        } catch (Exception e) {
            return "redirect:/order?depend";
        }
        return "redirect:/order";
    }
}
