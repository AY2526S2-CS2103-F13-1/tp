package seedu.blockbook.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.blockbook.logic.parser.Prefix;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_GAMERS_LISTED_COUNT = "%1$d gamers listed!";
    public static final String MESSAGE_GAMERS_LISTED_OVERVIEW = "Listed all gamers.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_CONTACT_LIST = "No contacts to delete. The list is empty.";
    public static final String MESSAGE_NO_CONTACTS = "List loaded but empty.";
    public static final String MESSAGE_DISPLAY_CONTACTS_ERROR = "Error displaying contacts.";
    public static final String MESSAGE_INDEX_OUT_OF_RANGE = "Please provide a valid index. Index is out of range.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code gamer} for display to the user.
     */
    public static String format(Gamer gamer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(gamer.getName())
                .append("; GamerTag: ")
                .append(gamer.getGamerTag());
        // .append("; Email: ")
        // .append(gamer.getEmail())
        // .append("; Address: ")
        // .append(gamer.getAddress())
        // .append("; Tags: ");
        // gamer.getTags().forEach(builder::append);
        return builder.toString();
    }

}

