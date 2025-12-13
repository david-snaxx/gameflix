package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import com.example.gameflixbackend.gamemanagement.service.GameServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GameListingPageController {

    private final GameServiceImpl gameService;
    private final GameRepository gameRepository;

    public GameListingPageController(GameServiceImpl gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
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
}
