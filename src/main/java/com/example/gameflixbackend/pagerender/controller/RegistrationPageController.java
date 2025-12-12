package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationPageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String renderRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistrationForm(@ModelAttribute User user) {
        try {
            this.userService.save(user);
            return "redirect:/register-success";
        } catch (Exception e) {
            return "redirect:/register-failure";
        }

    }

    @GetMapping("/register-success")
    public String renderRegistrationSuccessPage(Model model) {
        return "register-success";
    }
}
