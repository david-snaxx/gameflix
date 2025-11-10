package com.example.gameflixbackend.gamemanagement.service;

import com.example.gameflixbackend.gamemanagement.model.IgdbGame;
import com.example.gameflixbackend.gamemanagement.model.IgdbSearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * The IgdbService handles making requests to the Twitch Internet Games Database which is a public
 * database for retrieving public game metadata. This service is primarily concerned with making simple GET
 * request for game data.
 */
@Service
public class IgdbService {

    private static final String BASE_URL = "https://api.igdb.com/v4/games";
    private final RestTemplate restTemplate;
    private final TwitchAuthenticationService twitchAuthenticationService;
    private final String IgdbClientId;

    public IgdbService(RestTemplate restTemplate,
                       TwitchAuthenticationService twitchAuthService,
                       @Value("${igdb.client-id}") String IgdbClientId) {
        this.restTemplate = restTemplate;
        this.twitchAuthenticationService = twitchAuthService;
        this.IgdbClientId = IgdbClientId;
    }

    /**
     * Constructs the authorization headers needed to make calls to the IGDB.
     * @return An {@link HttpHeaders} object for making IDGB API calls.
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", this.IgdbClientId);
        headers.set("Authorization", "Bearer " + this.twitchAuthenticationService.getTwitchAccessToken());
        headers.set("Accept", "application/json");
        return headers;
    }


    /**
     * Search the IGDB for the 5 closets matching games by input name.
     * @param gameName The name of the game to search for.
     * @return A JSON string containing {@link IgdbService}s where the first is the closest matching game.
     */
    public IgdbSearchResult[] searchForGames(String gameName) {
        // igdb search query: basic summary info, searching with name, only give 5 results
        String query = String.format("fields name, summary; search \"%s\"; limit 5;", gameName);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        ResponseEntity<IgdbSearchResult[]> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                entity,
                IgdbSearchResult[].class
        );
        return response.getBody();
    }

    /**
     * Gets all relevant info for a game from the IGDB based on its IGDB id.
     * Note that the {@link IgdbGame} object returned contains IGDB ids and not fully defined info.
     * @param IgdbGameId The IGDB id for the game to search for.
     * @return A {@link IgdbGame} object representing the game from the IGDB.
     */
    public IgdbGame getFullyDefinedGameById(Integer IgdbGameId) {
        // igdb search query: full info, searching with id, only give 1 result
        String query = String.format("fields *; where id = %d; limit 1;", IgdbGameId);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        ResponseEntity<IgdbGame[]> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                entity,
                IgdbGame[].class
        );
        return response.getBody()[0];
    }
}
