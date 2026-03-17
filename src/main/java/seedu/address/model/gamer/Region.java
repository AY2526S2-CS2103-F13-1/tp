package seedu.address.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's region in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Region {

    public static final String MESSAGE_CONSTRAINTS =
            "Region should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullRegion;

    /**
     * Constructs a {@code Name}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidName(region), MESSAGE_CONSTRAINTS);
        fullRegion = region;
    }

    /**
     * Returns true if a given string is a valid region.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullRegion;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Region)) {
            return false;
        }

        Region otherName = (Region) other;
        return fullRegion.equals(otherName.fullRegion);
    }

    @Override
    public int hashCode() {
        return fullRegion.hashCode();
    }

}
