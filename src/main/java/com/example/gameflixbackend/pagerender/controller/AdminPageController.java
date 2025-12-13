package com.example.gameflixbackend.pagerender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String renderAdminIndexPage(Model model) {
        return "admin-index";
    }
}
