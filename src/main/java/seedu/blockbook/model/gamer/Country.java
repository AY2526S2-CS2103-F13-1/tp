package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's country in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidCountry(String)}
 */
public class Country {


    public static final String MESSAGE_CONSTRAINTS =
            "Country should only contain letters, spaces, and hyphens, "
                    + "and be at most 50 characters.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z \\\\-]{1,50}$";
    public final String fullCountry;

    /**
     * Constructs a {@code Country}.
     *
     * @param country A valid country.
     */
    public Country(String country) {
        requireNonNull(country);
        checkArgument(isValidCountry(country), MESSAGE_CONSTRAINTS);
        fullCountry = country;
    }

    /**
     * Returns true if a given string is a valid country.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullCountry;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Country)) {
            return false;
        }

        Country otherCountry = (Country) other;
        return fullCountry.equals(otherCountry.fullCountry);
    }

    @Override
    public int hashCode() {
        return fullCountry.hashCode();
    }

}

