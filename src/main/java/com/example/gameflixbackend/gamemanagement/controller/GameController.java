package com.example.gameflixbackend.gamemanagement.controller;

import com.example.gameflixbackend.gamemanagement.model.IgdbSearchResult;
import com.example.gameflixbackend.gamemanagement.service.IgdbService;
import com.example.gameflixbackend.gamemanagement.service.TwitchAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private IgdbService igdbService;

    @Autowired
    TwitchAuthenticationService twitchAuthenticationService;

    /**
     * Searches for the 5 closest matching games in the IGDB based on the input name.
     * The first result is the closest absolute matching game by name (but may not exactly equal the input).
     * @param gameName The name of the game to search for in the IGDB database.
     * @return The 5 closest matching games in the IGDB based on the input name.
     */
    @RequestMapping(value = "/igdb/search/name/{gameName}", method = RequestMethod.GET)
    public ResponseEntity<?> getGame(@PathVariable("gameName") String gameName) {
        try {
            IgdbSearchResult[] results = this.igdbService.searchForGames(gameName);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtains the currently valid IGDB access token.
     * //todo: This is more of a testing endpoint and isn't needed in the final product
     * @return The currently valid IGDB access token.
     */
    @RequestMapping(value = "/igdb/accesstoken", method = RequestMethod.GET)
    public ResponseEntity<String> getAccessToken() {
        try {
            String jsonResponse = this.twitchAuthenticationService.getTwitchAccessToken();
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "igdb/search/id/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameById(@PathVariable("gameId") Integer gameId) {
        try {
            String result = this.igdbService.getFullyDefinedGameById(gameId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
