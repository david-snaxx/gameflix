package com.example.gameflixbackend.gamemanagement.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the ENUM for how the IGDB stores age rating value information for a game.
 */
public enum IgdbAgeRatingValue {

    UNKNOWN(0, "Not Rated"),
    PEGI_3(1, "3"),
    PEGI_7(2, "7"),
    PEGI_12(3, "12"),
    PEGI_16(4, "16"),
    PEGI_18(5, "18"),
    ESRB_RP(6, "RP (Rating Pending)"),
    ESRB_EC(7, "EC (Early Childhood)"),
    ESRB_E(8, "E (Everyone)"),
    ESRB_E10(9, "E10+ (Everyone 10+)"),
    ESRB_T(10, "T (Teen)"),
    ESRB_M(11, "M (Mature 17+)"),
    ESRB_AO(12, "AO (Adults Only 18+)"),
    CERO_A(13, "A (All Ages)"),
    CERO_B(14, "B (12+)"),
    CERO_C(15, "C (15+)"),
    CERO_D(16, "D (17+)"),
    CERO_Z(17, "Z (18+)"),
    USK_0(18, "0 (All Ages)"),
    USK_6(19, "6"),
    USK_12(20, "12"),
    USK_16(21, "16"),
    USK_18(22, "18"),
    GRAC_ALL(23, "All"),
    GRAC_12(24, "12+"),
    GRAC_15(25, "15+"),
    GRAC_18(26, "18+"),
    GRAC_TESTING(27, "Testing"),
    CLASS_IND_L(28, "L (All Ages)"),
    CLASS_IND_10(29, "10"),
    CLASS_IND_12(30, "12"),
    CLASS_IND_14(31, "14"),
    CLASS_IND_16(32, "16"),
    CLASS_IND_18(33, "18"),
    ACB_G(34, "G (General)"),
    ACB_PG(35, "PG (Parental Guidance)"),
    ACB_M(36, "M (Mature)"),
    ACB_MA15(37, "MA15+"),
    ACB_R18(38, "R18+"),
    ACB_RC(39, "RC (Refused Classification)");

    private final int id;
    private final String ratingName;

    IgdbAgeRatingValue(int id, String ratingName) {
        this.id = id;
        this.ratingName = ratingName;
    }

    public int getId() {
        return id;
    }

    public String getRatingName() {
        return ratingName;
    }

    // lookup map for id -> rating translation
    private static final Map<Integer, IgdbAgeRatingValue> LOOKUP = new HashMap<>();
    static {
        for (IgdbAgeRatingValue rating : IgdbAgeRatingValue.values()) {
            LOOKUP.put(rating.getId(), rating);
        }
    }

    /**
     * Translates a given ID into the associated String value for the age rating value.
     * @param id The category id
     * @return A string representing the matching value name for the category id.
     */
    public static String fromId(int id) {
        IgdbAgeRatingValue match = LOOKUP.getOrDefault(id, UNKNOWN);
        return match.ratingName;
    }
}
