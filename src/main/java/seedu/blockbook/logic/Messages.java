package seedu.blockbook.logic;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.blockbook.logic.parser.Prefix;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! Type help to view all available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n\n%1$s";
    public static final String MESSAGE_GAMERS_LISTED_COUNT = "%1$d gamers listed!";
    public static final String MESSAGE_GAMERTAG_NOT_FOUND = "Gamertag not found. Use a different gamertag instead!";
    public static final String MESSAGE_GAMERS_LISTED_OVERVIEW = "Listed all gamers.";
    public static final String MESSAGE_NO_GAMERS_FOUND_BY_FIND = "BlockBook could not find any"
            + " gamer contacts based on search criteria. Search with a different keyword. ";
    public static final String MESSAGE_GAMERS_FOUND_BY_FIND =
            "%1$d gamer(s) found based on global search criteria.";
    public static final String MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC =
            "%1$d gamer(s) found based on search criteria: %2$s";
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
        builder.append("Gamertag: ")
                .append(formatNullable(gamer.getGamerTag()))
                .append(" Name: ")
                .append(formatNullable(gamer.getName()))
                .append(" Phone: ")
                .append(formatNullable(gamer.getPhone()))
                .append(" Email: ")
                .append(formatNullable(gamer.getEmail()))
                .append(" Group: ")
                .append(formatGroups(gamer.getGroups()))
                .append(" Server: ")
                .append(formatNullable(gamer.getServer()))
                .append(" Country: ")
                .append(formatNullable(gamer.getCountry()))
                .append(" Region: ")
                .append(formatNullable(gamer.getRegion()))
                .append(" Note: ")
                .append(formatNullable(gamer.getNote()));
        return builder.toString();
    }

    /**
     * Formats a compact gamer summary for status messages.
     */
    public static String formatContactSummary(Gamer gamer) {
        requireNonNull(gamer);
        return String.format("\n Name: %s\n GamerTag: %s",
                formatNullable(gamer.getName()),
                formatNullable(gamer.getGamerTag()));
    }

    /**
     * Formats a list of groups for display.
     */
    private static String formatGroups(List<Group> groups) {
        if (groups == null || groups.isEmpty()) {
            return formatNullable(null);
        }
        return groups.stream()
                .map(Group::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns "N/A" if the field is null, otherwise returns its string form.
     */
    public static String formatNullable(Object field) {
        return field == null ? "N/A" : field.toString();
    }

}
