package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.model.IgdbSearchResult;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import com.example.gameflixbackend.gamemanagement.service.IgdbService;
import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AdminPageController {

    private IgdbService igdbService;
    private GameRepository gameRepository;
    private UserRepository userRepository;

    public AdminPageController(IgdbService igdbService, GameRepository gameRepository, UserRepository userRepository) {
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
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
                return "redirect:/admin/igdb-search?error=duplicate";
            }
            this.gameRepository.save(game);
            return "redirect:/admin/igdb-search?success=true";
        }
        return "redirect:/admin/igdb-search?error=generic";
    }

    @GetMapping("/admin/all-games")
    public String renderAllGamesPage(Model model) {
        List<Game> games = this.gameRepository.findAll();
        model.addAttribute(games);
        return "admin-game-list";
    }

    @PostMapping("admin/all-games/delete")
    public String removeGameFromDatabase(@RequestParam("gameName") String gameName) {
        Game toDelete = this.gameRepository.findByName(gameName);
        if (Objects.nonNull(toDelete)) {
            this.gameRepository.delete(toDelete);
        }
        return "redirect:/admin/all-games";
    }

    @GetMapping("admin/all-users")
    public String renderAllUsersPage(Model model) {
        List<User> users = this.userRepository.findAll();
        model.addAttribute(users);
        return "admin-users-list";
    }

    @PostMapping("admin/all-users/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "redirect:/admin/all-users?success=false";
        }

        User user = optionalUser.get();
        this.userRepository.delete(user);
        return "redirect:/admin/all-users?success=true";
    }

    @PostMapping("admin/all-users/toggleSub")
    public String toggleSubscriptionState(@RequestParam("userId") Long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "redirect:/admin/all-users?success=false";
        }

        User user = optionalUser.get();
        // flip-flop the subscriber boolean
        if (user.isSubscribed) {
            user.setSubscribed(false);
        } else {
            user.setSubscribed(true);
        }
        this.userRepository.save(user);
        return "redirect:/admin/all-users?success=true";
    }

    @PostMapping("admin/all-users/toggleAdmin")
    public String toggleAdminState(@RequestParam("userId") Long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "redirect:/admin/all-users?success=true";
        }

        User user = optionalUser.get();
        // flip-flop the admin boolean
        if (user.isAdmin) {
            user.setAdmin(false);
        } else {
            user.setAdmin(true);
        }
        this.userRepository.save(user);
        return "redirect:/admin/all-users?success=true";
    }
}
