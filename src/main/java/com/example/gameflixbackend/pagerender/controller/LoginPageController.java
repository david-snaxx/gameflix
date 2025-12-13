package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.usermanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String renderLoginPage(Model model) {
        return "login";
    }
}
