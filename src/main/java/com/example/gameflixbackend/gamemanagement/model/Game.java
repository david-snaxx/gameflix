package com.example.gameflixbackend.gamemanagement.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long igdbId;
    public String name;
    public String summary;
    public String storyline;
    public Long firstReleaseDate;
    public List<String> ageRatings;
    public List<String> alternativeNames;
    public List<String> artworkUrls;
    public String coverUrl;
    public List<String> expansions;
    public List<String> genres;
    public List<String> developers;
    public List<String> publishers;
    public List<String> keywords;
    public List<String> platforms;
    public List<String> screenshotUrls;
    public List<String> themes;
    public List<String> videoUrls;
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
