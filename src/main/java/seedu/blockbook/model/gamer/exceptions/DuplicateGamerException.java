package seedu.blockbook.model.gamer.exceptions;

/**
 * Signals that the operation will result in duplicate gamers (gamers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateGamerException extends RuntimeException {
    public DuplicateGamerException() {
        super("Operation would result in duplicate gamers");
    }
}

