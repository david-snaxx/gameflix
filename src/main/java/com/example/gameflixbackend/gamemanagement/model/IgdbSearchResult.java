package com.example.gameflixbackend.gamemanagement.model;

/**
 * Represents a search result with very basic game data.
 * This can be used to verify search results before getting the rest of a games metadata.
 * the {@code games/igdb/search/{gamename}} endpoint returns this object.
 */
public class IgdbSearchResult {

    public Integer id;
    public String name;
    public String summary;
}
