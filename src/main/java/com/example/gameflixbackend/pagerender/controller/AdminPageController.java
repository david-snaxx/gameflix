package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.model.IgdbSearchResult;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import com.example.gameflixbackend.gamemanagement.service.IgdbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class AdminPageController {

    private IgdbService igdbService;
    private GameRepository gameRepository;

    public AdminPageController(IgdbService igdbService, GameRepository gameRepository) {
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/admin")
    public String renderAdminIndexPage(Model model) {
        return "admin-index";
    }

    @GetMapping("/admin/igdb-search")
    public String renderIgdbSearchPage(@RequestParam(name = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            IgdbSearchResult[] searchResults = this.igdbService.searchForGames(query);
            model.addAttribute("searchResults", searchResults);
            model.addAttribute("query", query);
        }
        return "admin-igdb-search";
    }

    @PostMapping("/admin/igdb-search/add")
    public String saveGameFromSearch(@RequestParam("igdbId") Integer igdbId) {
        Game game = this.igdbService.getFullyDefinedGameById(igdbId);
        if (Objects.nonNull(game)) {
            // duplicates are not allowed
            if (this.gameRepository.existsByName(game.name)) {
                return "redirect:/admin/igdb-search?error=duplicate&query=" + game.name;
            }
            this.gameRepository.save(game);
            return "redirect:/admin/igdb-search?success=true&query=" + game.name;
        }
        return "redirect:/admin/igdb-search?error=generic";
    }
}
