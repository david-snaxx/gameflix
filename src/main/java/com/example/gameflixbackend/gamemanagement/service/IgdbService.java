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

    private static final String BASE_URL = "https_//api.igdb.com/v4/games";
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
}
