package com.example.gameflixbackend.gamemanagement.service;

import com.example.gameflixbackend.gamemanagement.model.TwitchAccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * The TwitchAuthenticationService is used to make a request for the latest authorization token needed to make
 * API calls to the Internet Games Database (IGDB) which provides public game metadata.
 */
@Service
public class TwitchAuthenticationService {

    private final RestTemplate restTemplate;
    private final String igdbClientId;
    private final String igdbClientSecret;
    private final String authApiUrl;

    public TwitchAuthenticationService(RestTemplate restTemplate,
                             @Value("${igdb.client-id}") String clientId,
                             @Value("${igdb.client-secret}") String clientSecret) {
        this.restTemplate = restTemplate;
        this.igdbClientId = clientId;
        this.igdbClientSecret = clientSecret;
        this.authApiUrl = "https://id.twitch.tv/oauth2/token" +
                "?client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=client_credentials";
    }

    /**
     * Attempts to get the access token needed to authorize API calls to the Internet Games Database
     * @return The IGDB access token for the GameFlix application
     */
    public String getTwitchAccessToken() {
        try {
            TwitchAccessToken accessToken = restTemplate.postForObject(authApiUrl, null, TwitchAccessToken.class);
            Objects.requireNonNull(accessToken, "Failed to get auth token from Twitch");
            return accessToken.accessToken;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
