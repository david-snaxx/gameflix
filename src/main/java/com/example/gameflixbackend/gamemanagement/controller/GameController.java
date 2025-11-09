package com.example.gameflixbackend.gamemanagement.controller;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.model.IgdbSearchResult;
import com.example.gameflixbackend.gamemanagement.service.GameService;
import com.example.gameflixbackend.gamemanagement.service.IgdbService;
import com.example.gameflixbackend.gamemanagement.service.TwitchAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private IgdbService igdbService;

    @Autowired
    private TwitchAuthenticationService twitchAuthenticationService;

    @Autowired
    private GameService gameService;

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

    /**
     * Retrieves a {@link Game} object based on the information received from the IGDB.
     * @param gameId The IGDB game id to search for.
     * @return A {@link Game} object defined by the metadata for the input game on the IGDB.
     */
    @RequestMapping(value = "/igdb/search/id/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameById(@PathVariable("gameId") Integer gameId) {
        try {
            Game result = this.igdbService.getFullyDefinedGameById(gameId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Adds a game to the GameFlix database based on the input IGDB game ID.
     * This method assumes that a valid IGDB id is given.
     * @param gameId The IGDB game id to add.
     * @return A response entity for operation success/failure.
     */
    @RequestMapping(value = "/add/igdb/{gameId}", method = RequestMethod.POST)
    public ResponseEntity<?> adddIgdbGame(@PathVariable("gameId") Integer gameId) {
        try {
            Game result = this.igdbService.getFullyDefinedGameById(gameId);
            this.gameService.save(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIgdbGame(@PathVariable("id") Long id) {
        try {
            this.gameService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getIgdbGame(@PathVariable("id") Long id) {
        try {
            Optional<Game> game = this.gameService.findById(id);
            if (game.isPresent()) {
                return ResponseEntity.ok(game);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
