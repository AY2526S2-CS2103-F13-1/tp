package seedu.blockbook.model.gamer.exceptions;

/**
 * Signals that the operation would result in duplicate groups (groups are considered case-insensitive).
 */
public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
