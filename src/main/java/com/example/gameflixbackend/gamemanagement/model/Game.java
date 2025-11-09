package com.example.gameflixbackend.gamemanagement.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "igdb_id", unique = true, nullable = true)
    public Long igdbId;

    @Column(name = "name", unique = true, nullable = true)
    public String name;

    @Column(name = "summary", columnDefinition = "TEXT", nullable = true)
    public String summary;

    // this is too long
//    @Column(name = "storyline", columnDefinition = "TEXT", nullable = true)
//    public String storyline;

    @Column(name = "first_release_date", nullable = true)
    public Long firstReleaseDate;

    @ElementCollection
    @CollectionTable(name = "game_age_ratings", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> ageRatings;

    @ElementCollection
    @CollectionTable(name = "game_alternative_names", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> alternativeNames;

    @ElementCollection
    @CollectionTable(name = "game_artwork_urls", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> artworkUrls;

    @Column(name = "cover_url", columnDefinition = "TEXT", nullable = true)
    public String coverUrl; // This one is a single String, so it's fine

    @ElementCollection
    @CollectionTable(name = "game_expansions", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> expansions;

    @ElementCollection
    @CollectionTable(name = "game_genres", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> genres;

    @ElementCollection
    @CollectionTable(name = "game_developers", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> developers;

    @ElementCollection
    @CollectionTable(name = "game_publishers", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> publishers;

    @ElementCollection
    @CollectionTable(name = "game_keywords", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> keywords;

    @ElementCollection
    @CollectionTable(name = "game_platforms", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> platforms;

    @ElementCollection
    @CollectionTable(name = "game_screenshot_urls", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> screenshotUrls;

    @ElementCollection
    @CollectionTable(name = "game_themes", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> themes;

    @ElementCollection
    @CollectionTable(name = "game_video_urls", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> videoUrls;

    @ElementCollection
    @CollectionTable(name = "game_website_urls", joinColumns = @JoinColumn(name = "game_id"))
    public List<String> websiteUrls;

    public Game () {}

    public Game(Long igdbId, String name, String summary, String storyline,
                Long firstReleaseDate, List<String> ageRatings, List<String> alternativeNames,
                List<String> artworkUrls, String coverUrl, List<String> expansions,
                List<String> genres, List<String> developers, List<String> publishers,
                List<String> keywords, List<String> platforms, List<String> screenshotUrls,
                List<String> themes, List<String> videoUrls, List<String> websiteUrls) {
        this.igdbId = igdbId;
        this.name = name;
        this.summary = summary;
//        this.storyline = storyline;
        this.firstReleaseDate = firstReleaseDate;
        this.ageRatings = ageRatings;
        this.alternativeNames = alternativeNames;
        this.artworkUrls = artworkUrls;
        this.coverUrl = coverUrl;
        this.expansions = expansions;
        this.genres = genres;
        this.developers = developers;
        this.publishers = publishers;
        this.keywords = keywords;
        this.platforms = platforms;
        this.screenshotUrls = screenshotUrls;
        this.themes = themes;
        this.videoUrls = videoUrls;
        this.websiteUrls = websiteUrls;
    }
}
