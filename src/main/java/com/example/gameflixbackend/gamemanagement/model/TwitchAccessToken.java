package com.example.gameflixbackend.gamemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a twitch authorization access token for working with the Internet Games Database
 */
public class TwitchAccessToken {

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("expires_in")
    public int expiresIn;

    @JsonProperty("token_type")
    public String tokenType;
}
