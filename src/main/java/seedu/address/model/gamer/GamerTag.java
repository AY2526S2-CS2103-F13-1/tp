package seedu.address.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGamerTag(String)}
 */
public class GamerTag {

    public static final String MESSAGE_CONSTRAINTS =
            "GamerTags should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * GamerTags should only be max 50 chars long, A-Z, a-z, numbers, no spaces,
     * and only allow the special character of underscore (_).
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]{1,50}$";

    public final String fullGamerTag;

    /**
     * Constructs a {@code Name}.
     *
     * @param gamerTag A valid name.
     */
    public GamerTag(String gamerTag) {
        requireNonNull(gamerTag);
        checkArgument(isValidGamerTag(gamerTag), MESSAGE_CONSTRAINTS);
        fullGamerTag = gamerTag;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidGamerTag(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullGamerTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GamerTag)) {
            return false;
        }

        GamerTag otherName = (GamerTag) other;
        return fullGamerTag.equals(otherName.fullGamerTag);
    }

    @Override
    public int hashCode() {
        return fullGamerTag.hashCode();
    }

}
