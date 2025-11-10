package com.example.gameflixbackend.gamemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a game as given from the Internet Games Database.
 * All items in this object are IDs that need to be resolved against various endpoints of the IGDB API.
 */
public class IgdbGame {

    @JsonProperty("id")
    public Integer igdbId;

    @JsonProperty("age_ratings")
    public List<Integer> ageRatings;

    @JsonProperty("alternative_names")
    public List<Integer> alternativeNames;

    @JsonProperty("artworks")
    public List<Integer> artworks;

    @JsonProperty("cover")
    public Integer cover;

    @JsonProperty("expansions")
    public List<Integer> expansions;

    @JsonProperty("first_release_date")
    public Integer firstReleaseDate;

    @JsonProperty("genres")
    public List<Integer> genres;

    @JsonProperty("involved_companies")
    public List<Integer> involvedCompanies;

    @JsonProperty("keywords")
    public List<Integer> keywords;

    @JsonProperty("name")
    public String name;

    @JsonProperty("platforms")
    public List<Integer> platforms;

    @JsonProperty("screenshots")
    public List<Integer> screenshots;

    @JsonProperty("storyline")
    public String storyline;

    @JsonProperty("summary")
    public String summary;

    @JsonProperty("tags")
    public List<Integer> tags;

    @JsonProperty("themes")
    public List<Integer> themes;

    @JsonProperty("videos")
    public List<Integer> videos;

    @JsonProperty("websites")
    public List<Integer> websites;
}
