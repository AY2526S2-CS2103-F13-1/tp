package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's personal note in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {


    public static final String MESSAGE_CONSTRAINTS =
            "Notes should only contain letters, numbers, spaces, underscores (_), hyphens (-), "
                    + "and apostrophes ('), and be at most 50 characters long.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_ '\\-]{1,50}$";
    public final String fullNote;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        fullNote = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullNote;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return fullNote.equals(otherNote.fullNote);
    }

    @Override
    public int hashCode() {
        return fullNote.hashCode();
    }

}

