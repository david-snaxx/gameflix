package com.example.gameflixbackend.gamemanagement.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
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
    @RequestMapping(value = "/{gameName}", method = RequestMethod.GET)
    public ResponseEntity<String> getGame(@PathVariable("gameName") String gameName) {
        try {
            // This will now return the raw JSON string
            String jsonResponse = this.igdbService.makeRequest(gameName);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            // Return the exception message as the error
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
}
