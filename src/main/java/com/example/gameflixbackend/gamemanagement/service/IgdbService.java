package com.example.gameflixbackend.gamemanagement.service;

import com.example.gameflixbackend.gamemanagement.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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
     * Gets a {@link IgdbGame} based on the given IGDB game id.
     * The object returned should be translated into an {@link com.example.gameflixbackend.gamemanagement.model.Game} object
     * before being stored in the database.
     * @param IgdbGameId The IGDB id of the game to find.
     * @return An {@link IgdbGame} object representing the game for the input ID.
     */
    public Game getFullyDefinedGameById(Integer IgdbGameId) {
        // igdb search query: full info, searching with id, only give 1 result
        String query = String.format("fields \n" +
                "    id,\n" +
                "    name,\n" +
                "    summary,\n" +
                "    storyline,\n" +
                "    first_release_date,\n" +
                "    age_ratings.*,\n" +
                "    alternative_names.name,\n" +
                "    artworks.image_id, artworks.url,\n" +
                "    cover.*,\n" +
                "    expansions.name,\n" +
                "    genres.name,\n" +
                "    involved_companies.developer, involved_companies.publisher, involved_companies.company.name,\n" +
                "    keywords.name,\n" +
                "    platforms.name,\n" +
                "    screenshots.image_id, screenshots.url,\n" +
                "    themes.name,\n" +
                "    videos.name, videos.video_id,\n" +
                "    websites.url, websites.category\n" +
                ";\n" +
                "where id = %d;\n" +
                "limit 1;", IgdbGameId);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        ResponseEntity<IgdbGame[]> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                entity,
                IgdbGame[].class
        );
        return this.translateIgdbGame(response.getBody()[0]);
    }

    /**
     * Handles lists to prevent null access during streams.
     * @param list The list that may or may not be null.
     * @return A guaranteed list object that will simply be empty if the list was null.
     */
    private <T> List<T> handleList(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * Transforms a {@link IgdbGame} into an {@link Game}.
     * Game objects are fully defined game metadata objects (i.e. full text instead of id based) and
     * are ready for storage in the database.
     * @param igdbGame The {@link IgdbGame} object.
     * @return A {@link Game} object constructed using the input IGDB object.
     */
    private Game translateIgdbGame(IgdbGame igdbGame) {
        // age ratings are ID based when direct from IGDB
        List<String> fullAgeRatings = igdbGame.ageRatings.stream()
                .map(ageRating -> {
                    String organization = IgdbAgeRatingCategory.fromId(ageRating.category);
                    String ratingVal = IgdbAgeRatingValue.fromId(ageRating.rating);
                    return organization + " " + ratingVal;
                })
                .toList();

        // devs and publishers are stored in the same initial list, but have boolean flags
        List<String> fullDevelopers = new ArrayList<>();
        List<String> fullPublishers = new ArrayList<>();
        igdbGame.involvedCompanies.stream()
                .forEach(company -> {
                    if (company.developer) {
                        fullDevelopers.add(company.getCompany().getName());
                    } else if (company.publisher) {
                        fullPublishers.add(company.getCompany().getName());
                    }
                });

        return new Game(
                igdbGame.id,
                igdbGame.name,
                igdbGame.summary,
                igdbGame.storyline,
                igdbGame.firstReleaseDate,
                fullAgeRatings,
                this.handleList(igdbGame.alternativeNames).stream().map(IgdbGame.SimpleReference::getName).toList(),
                this.handleList(igdbGame.artworks).stream().map(IgdbGame.ImageInfo::getUrl).toList(),
                igdbGame.cover != null ? igdbGame.cover.url : null,
                this.handleList(igdbGame.expansions).stream().map(IgdbGame.SimpleReference::getName).toList(),
                this.handleList(igdbGame.genres).stream().map(IgdbGame.SimpleReference::getName).toList(),
                fullDevelopers,
                fullPublishers,
                this.handleList(igdbGame.keywords).stream().map(IgdbGame.SimpleReference::getName).toList(),
                this.handleList(igdbGame.platforms).stream().map(IgdbGame.SimpleReference::getName).toList(),
                this.handleList(igdbGame.screenshots).stream().map(IgdbGame.ImageInfo::getUrl).toList(),
                this.handleList(igdbGame.themes).stream().map(IgdbGame.SimpleReference::getName).toList(),
                this.handleList(igdbGame.videos).stream().map(IgdbGame.Video::getVideoId).toList(),
                this.handleList(igdbGame.websites).stream().map(IgdbGame.Website::getUrl).toList()
        );
    }
}
