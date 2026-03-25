package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's group in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {


    public static final String MESSAGE_CONSTRAINTS =
            "Group should only contain letters, spaces, hyphens, and apostrophes, "
                    + "and be at most 50 characters.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z' \\-]{1,50}$";
    public final String fullGroup;

    /**
     * Constructs a {@code Group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_CONSTRAINTS);
        fullGroup = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullGroup;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return fullGroup.equals(otherGroup.fullGroup);
    }

    @Override
    public int hashCode() {
        return fullGroup.hashCode();
    }

}

