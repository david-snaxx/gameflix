package com.example.gameflixbackend.gamemanagement.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the ENUM for how the IGDB stores age rating organization information for a game.
 */
public enum IgdbAgeRatingCategory {
    UNKNOWN(0, "Unknown"),
    ESRB(1, "ESRB"),
    PEGI(2, "PEGI"),
    CERO(3, "CERO"),
    USK(4, "USK"),
    GRAC(5, "GRAC"),
    CLASS_IND(6, "CLASS_IND"),
    ACB(7, "ACB");

    private final int id;
    private final String organizationName;

    IgdbAgeRatingCategory(int id, String organizationName) {
        this.id = id;
        this.organizationName = organizationName;
    }

    public int getId() {
        return id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    // lookup map for id -> organization name translation
    private static final Map<Integer, IgdbAgeRatingCategory> LOOKUP = new HashMap<>();
    static {
        for (IgdbAgeRatingCategory category : IgdbAgeRatingCategory.values()) {
            LOOKUP.put(category.getId(), category);
        }
    }

    /**
     * Translates a given ID into the associated String value for the age rating category.
     * @param id The category id
     * @return A string representing the matching organization name for the category id.
     */
    public static String fromId(int id) {
        IgdbAgeRatingCategory match = LOOKUP.getOrDefault(id, UNKNOWN);
        return match.getOrganizationName();
    }
}
