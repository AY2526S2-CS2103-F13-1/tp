package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;

/**
 * Supported favourite status values for a gamer.
 * FAV represents a favourite gamer, while UNFAV represents a non-favourite gamer.
 */
public enum FavouriteStatus {
    FAV("fav"),
    UNFAV("unfav");

    private final String statusValue;

    FavouriteStatus(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue;
    }

    /**
     * Returns the matching FavouriteStatus for a persisted favourite value.
     */
    public static FavouriteStatus fromStatusValue(String statusValue) {
        requireNonNull(statusValue);
        for (FavouriteStatus status : values()) {
            if (status.statusValue.equalsIgnoreCase(statusValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unsupported favourite status: " + statusValue);
    }
}
