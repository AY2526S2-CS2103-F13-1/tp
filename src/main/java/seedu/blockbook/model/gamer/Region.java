package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's region in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {

    public static final String MESSAGE_CONSTRAINTS =
            "Region must be one of the supported values: NA, SA, EU, AFRICA, ASIA, OCEANIA, or ME.";

    public final String fullRegion;

    /**
     * Constructs a {@code Region}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidRegion(region), MESSAGE_CONSTRAINTS);
        fullRegion = region.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid region.
     */
    public static boolean isValidRegion(String test) {
        requireNonNull(test);
        try {
            RegionType.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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

        Region otherRegion = (Region) other;
        return fullRegion.equals(otherRegion.fullRegion);
    }

    @Override
    public int hashCode() {
        return fullRegion.hashCode();
    }

}

