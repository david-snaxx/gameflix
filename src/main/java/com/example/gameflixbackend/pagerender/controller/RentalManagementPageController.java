package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RentalManagementPageController {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public RentalManagementPageController(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/rentals")
    public String showRentals(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        // 1. Fetch Current Rentals
        // findAllById is a built-in JPA method that takes a List of IDs!
        List<Game> currentRentals = gameRepository.findAllById(user.getCurrentlyRentingIds());

        // 2. Fetch History (and remove current ones so they don't show up twice)
        List<Long> historyIds = new ArrayList<>(user.getRentalHistoryIds()); // Make a copy
        historyIds.removeAll(user.getCurrentlyRentingIds()); // Remove active rentals
        List<Game> historicalRentals = gameRepository.findAllById(historyIds);

        model.addAttribute("currentRentals", currentRentals);
        model.addAttribute("historicalRentals", historicalRentals);

        return "profile-manage-rentals";
    }
}
