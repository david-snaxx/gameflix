package com.example.gameflixbackend.gamemanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
     * ***ADDED: Method to make a test request to IGDB***
     * * Makes a simple test request to the IGDB API to find games matching a name.
     * @param gameName The name of the game to search for.
     * @return The raw JSON response string from the IGDB API.
     */
    public String makeRequest(String gameName) {
        // 1. Create the Apocalypso query string.
        // We "search" for the gameName and request a few fields.
        String query = String.format("fields name, summary; search \"%s\"; limit 5;", gameName);

        // 2. Get the required headers
        HttpHeaders headers = createHeaders();

        // 3. Create the HttpEntity, passing the query as the request body
        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        // 4. Make the POST request and ask for the response as a raw String
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                entity,
                String.class // We want the raw JSON response as a String
        );

        // 5. Return the response body
        return response.getBody();
    }
}
