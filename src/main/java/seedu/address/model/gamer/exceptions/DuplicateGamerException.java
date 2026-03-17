package seedu.address.model.gamer.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateGamerException extends RuntimeException {
    public DuplicateGamerException() {
        super("Operation would result in duplicate persons");
    }
}
