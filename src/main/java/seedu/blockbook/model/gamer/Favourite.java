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
     * Constructs a {@code Favourite}.
     *
     * @param favourite A valid favourite.
     */
    public Favourite(String favourite) {
        requireNonNull(favourite);
        checkArgument(isValidFavourite(favourite), MESSAGE_CONSTRAINTS);
        fullFavourite = favourite;
    }

    /**
     * Returns true if a given string is a valid favourite.
     */
    public static boolean isValidFavourite(String test) {
        // return test.matches(VALIDATION_REGEX);
        requireNonNull(test);
        try {
            FavouriteStatus.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        // return value;
        // return fullFavourite.toLowerCase();
        if (fullFavourite.equalsIgnoreCase("fav")) {
            return "Yes";
        }
        return "No";
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

