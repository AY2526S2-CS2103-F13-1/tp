package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's favourite in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidFavourite(String)}
 */
public class Favourite {
    public static final String MESSAGE_CONSTRAINTS =
            "Favourite must be either 'fav' or 'unfav'.";

    public final String fullFavourite;

    /**
     * Constructs a Favourite.
     *
     * @param favourite A valid favourite.
     */
    public Favourite(String favourite) {
        requireNonNull(favourite);
        checkArgument(isValidFavourite(favourite), MESSAGE_CONSTRAINTS);
        FavouriteStatus status = FavouriteStatus.fromStatusValue(favourite);
        fullFavourite = status.getStatusValue();
    }

    /**
     * Constructs a Favourite from a FavouriteStatus.
     */
    public static Favourite fromStatus(FavouriteStatus status) {
        requireNonNull(status);
        return new Favourite(status.getStatusValue());
    }

    /**
     * Returns true if a given string is a valid favourite.
     */
    public static boolean isValidFavourite(String test) {
        requireNonNull(test);
        try {
            FavouriteStatus.fromStatusValue(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns true if this favourite status represents a favourited gamer.
     */
    public boolean isFav() {
        return FavouriteStatus.fromStatusValue(fullFavourite) == FavouriteStatus.FAV;
    }

    @Override
    public String toString() {
        return isFav() ? "Yes" : "No";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Favourite)) {
            return false;
        }

        Favourite otherFavourite = (Favourite) other;
        return fullFavourite.equals(otherFavourite.fullFavourite);
    }

    @Override
    public int hashCode() {
        return fullFavourite.hashCode();
    }

}

