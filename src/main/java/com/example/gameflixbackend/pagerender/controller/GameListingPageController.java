package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import com.example.gameflixbackend.gamemanagement.service.GameServiceImpl;
import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class GameListingPageController {

    private final GameServiceImpl gameService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameListingPageController(GameServiceImpl gameService, GameRepository gameRepository, UserRepository userRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("games/all")
    public String renderAllGames(Model model) {
        List<Game> games = this.gameRepository.findAll();
        model.addAttribute(games);
        return "allgames";
    }

    @GetMapping("/games/details/{id}")
    public String showGameDetails(@PathVariable Long id, Model model) {
        Game game = this.gameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid game Id:" + id));
        model.addAttribute("game", game);
        return "game-listing";
    }

    @PostMapping("/games/rent")
    public String rentGame(@RequestParam("gameId") Long gameId, Principal principal) {

        // is this a subscribed user?
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        if (!Boolean.TRUE.equals(user.getSubscribed())) {
            return "subscriptions";
        }

        // are they already renting 2 games?
        if (user.getCurrentlyRentingIds().size() >= 2) {
            return "rent-limit-reached";
        }

        // are they already renting this game?
        if (user.getCurrentlyRentingIds().contains(gameId)) {
            return "rent-duplicate";
        }

        // rent
        user.addRental(gameId);
        userRepository.save(user);
        return "rent-success";
    }

    @PostMapping("/games/return")
    public String returnGame(@RequestParam("gameId") Long gameId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        // remove the rental from the list
        if (user.getCurrentlyRentingIds().contains(gameId)) {
            user.removeRental(gameId);
            userRepository.save(user);
        }

        return "rent-returned";
    }
}
