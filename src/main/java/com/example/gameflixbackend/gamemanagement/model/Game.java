package com.example.gameflixbackend.gamemanagement.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "igdbId", unique = true, nullable = true)
    public Long igdbId;

    @Column(name = "name", unique = true, nullable = true)
    public String name;

    @Column(name = "summary", unique = false, nullable = true)
    public String summary;

    @Column(name = "storyline", unique = false, nullable = true)
    public String storyline;

    @Column(name = "fist_release_date", unique = false, nullable = true)
    public Long firstReleaseDate;

    @Column(name = "age_rating", unique = false, nullable = true)
    public List<String> ageRatings;

    @Column(name = "alternative_name", unique = false, nullable = true)
    public List<String> alternativeNames;

    @Column(name = "artwork_url", unique = true, nullable = true)
    public List<String> artworkUrls;

    @Column(name = "cover_url", unique = true, nullable = true)
    public String coverUrl;

    @Column(name = "expansion", unique = false, nullable = true)
    public List<String> expansions;

    @Column(name = "genre", unique = false, nullable = true)
    public List<String> genres;

    @Column(name = "developer", unique = false, nullable = true)
    public List<String> developers;

    @Column(name = "publisher", unique = false, nullable = true)
    public List<String> publishers;

    @Column(name = "keyword", unique = false, nullable = true)
    public List<String> keywords;

    @Column(name = "platform", unique = false, nullable = true)
    public List<String> platforms;

    @Column(name = "screenshot_url", unique = false, nullable = true)
    public List<String> screenshotUrls;

    @Column(name = "theme", unique = false, nullable = true)
    public List<String> themes;

    @Column(name = "video_url", unique = false, nullable = true)
    public List<String> videoUrls;

    @Column(name = "website_url", unique = false, nullable = true)
    public List<String> websiteUrls;

    public Game(Long igdbId, String name, String summary, String storyline,
                Long firstReleaseDate, List<String> ageRatings, List<String> alternativeNames,
                List<String> artworkUrls, String coverUrl, List<String> expansions,
                List<String> genres, List<String> developers, List<String> publishers,
                List<String> keywords, List<String> platforms, List<String> screenshotUrls,
                List<String> themes, List<String> videoUrls, List<String> websiteUrls) {
        this.igdbId = igdbId;
        this.name = name;
        this.summary = summary;
        this.storyline = storyline;
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
