package com.example.gameflixbackend.gamemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a fully detailed game from the IGDB.
 * Most but not all fields contain all context needed, some fields are enum IDs that must be resolved when
 * translating this object into a {@link Game} object for database storage.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IgdbGame {

    @JsonProperty("id")
    public long id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("summary")
    public String summary;

    @JsonProperty("storyline")
    public String storyline;

    @JsonProperty("first_release_date")
    public long firstReleaseDate;

    @JsonProperty("age_ratings")
    public List<AgeRating> ageRatings;

    @JsonProperty("alternative_names")
    public List<SimpleReference> alternativeNames;

    @JsonProperty("artworks")
    public List<ImageInfo> artworks;

    @JsonProperty("cover")
    public ImageInfo cover;

    @JsonProperty("expansions")
    public List<SimpleReference> expansions;

    @JsonProperty("genres")
    public List<SimpleReference> genres;

    @JsonProperty("involved_companies")
    public List<InvolvedCompany> involvedCompanies;

    @JsonProperty("keywords")
    public List<SimpleReference> keywords;

    @JsonProperty("platforms")
    public List<SimpleReference> platforms;

    @JsonProperty("screenshots")
    public List<ImageInfo> screenshots;

    @JsonProperty("themes")
    public List<SimpleReference> themes;

    @JsonProperty("videos")
    public List<Video> videos;

    @JsonProperty("websites")
    public List<Website> websites;

    /**
     * A basic id & name mapping object.
     * The name value is the important variable but the IGDB API returns both values.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SimpleReference {
        @JsonProperty("id")
        public long id;
        @JsonProperty("name")
        public String name;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * {@link IgdbAgeRatingCategory} = category <br>
     * {@link IgdbAgeRatingValue} = rating
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgeRating {
        @JsonProperty("id")
        public long id;
        @JsonProperty("category")
        public int category;
        @JsonProperty("rating")
        public int rating;

        public long getId() {
            return id;
        }

        public int getCategory() {
            return category;
        }

        public int getRating() {
            return rating;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageInfo {
        @JsonProperty("id")
        public long id;
        @JsonProperty("image_id")
        public String imageId;
        @JsonProperty("url")
        public String url;

        public long getId() {
            return id;
        }

        public String getImageId() {
            return imageId;
        }

        public String getUrl() {
            return url;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InvolvedCompany {
        @JsonProperty("id")
        public long id;
        @JsonProperty("developer")
        public boolean developer;
        @JsonProperty("publisher")
        public boolean publisher;
        @JsonProperty("company")
        public SimpleReference company;

        public long getId() {
            return id;
        }

        public boolean isDeveloper() {
            return developer;
        }

        public boolean isPublisher() {
            return publisher;
        }

        public SimpleReference getCompany() {
            return company;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Video {
        @JsonProperty("id")
        public long id;
        @JsonProperty("name")
        public String name;
        @JsonProperty("video_id")
        public String videoId;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getVideoId() {
            return videoId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Website {
        @JsonProperty("id")
        public long id;
        @JsonProperty("category")
        public int category;
        @JsonProperty("url")
        public String url;

        public long getId() {
            return id;
        }

        public int getCategory() {
            return category;
        }

        public String getUrl() {
            return url;
        }
    }
}
