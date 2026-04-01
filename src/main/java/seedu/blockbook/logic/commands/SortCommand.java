package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Sorts the contact list in BlockBook by the specified attributes.
 * Sorting is session-based and does not persist to storage.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts by the specified attributes. "
            + "Format: " + COMMAND_WORD + " [name/] [gamertag/] [phone/] [email/] "
            + "[group/] [server/] [favourite/] [country/] [region/] [note/]\n"
            + "Example: " + COMMAND_WORD + " phone/ gamertag/";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all contacts.";
    public static final String MESSAGE_SORT_DEFAULT_SUCCESS = "Sorted all contacts by gamertag (default).";
    public static final String MESSAGE_EMPTY_LIST = "There are no contacts to sort!";
    public static final String MESSAGE_INVALID_ATTRIBUTES =
            "Please ensure all attributes are valid. "
            + "Possible attributes: name, gamertag, phone, email, group, server, favourite, country, region, note";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE =
            "Duplicate attribute detected: '%1$s'. Each attribute can only be specified once.";
    public static final String MESSAGE_INVALID_ATTRIBUTE =
            "'%1$s' is not a valid attribute!\n"
            + COMMAND_WORD + ": Sorts contacts by the specified attributes. "
            + "Format: " + COMMAND_WORD + " [name/] [gamertag/] [phone/] [email/] "
            + "[group/] [server/] [favourite/] [country/] [region/] [note/]\n"
            + "Example: " + COMMAND_WORD + " phone/ gamertag/";

    public static final List<String> VALID_ATTRIBUTES = List.of(
            "name", "phone", "email", "group", "server", "favourite", "country", "region", "note", "gamertag"
    );

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final List<String> attributes;

    /**
     * Creates a SortCommand to sort by the specified attributes.
     *
     * @param attributes List of attribute names to sort by, in priority order.
     *                   Empty list means default sort by gamertag.
     */
    public SortCommand(List<String> attributes) {
        requireNonNull(attributes);
        this.attributes = attributes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing sort command with attributes: " + attributes);

        if (model.getFilteredGamerList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // Validate attributes
        for (String attr : attributes) {
            if (!VALID_ATTRIBUTES.contains(attr)) {
                throw new CommandException(MESSAGE_INVALID_ATTRIBUTES);
            }
        }

        // Build comparator based only on specified attributes (or gamertag by default).
        Comparator<Gamer> comparator = buildComparator();
        assert comparator != null : "Comparator should not be null after building";

        model.sortGamerList(comparator);

        String successMessage = attributes.isEmpty() ? MESSAGE_SORT_DEFAULT_SUCCESS : MESSAGE_SORT_SUCCESS;
        logger.info("Sort command executed successfully");
        return new CommandResult(successMessage);
    }

    /**
     * Builds a comparator from the specified sort attributes.
     */
    private Comparator<Gamer> buildComparator() {
        List<String> sortAttributes = attributes.isEmpty()
                ? List.of("gamertag") : attributes;
        assert !sortAttributes.isEmpty() : "Sort attributes should never be empty";

        Comparator<Gamer> comparator = getAttributeComparator(sortAttributes.get(0));
        for (int i = 1; i < sortAttributes.size(); i++) {
            String attr = sortAttributes.get(i);
            comparator = comparator.thenComparing(getAttributeComparator(attr));
        }

        return comparator;
    }

    /**
     * Returns a comparator for the given attribute name.
     * Null values are sorted to the end.
     */
    private Comparator<Gamer> getAttributeComparator(String attribute) {
        assert VALID_ATTRIBUTES.contains(attribute) : "Attribute should be valid at this point: " + attribute;
        if ("favourite".equals(attribute)) {
            // Explicit favourite sorting places favourites before non-favourites.
            return Comparator.comparing(
                    (Gamer g) -> g.getFavourite() == null ? null : g.getFavourite().isFav(),
                    Comparator.nullsLast(Comparator.reverseOrder())
            );
        }

        return Comparator.comparing((Gamer g) -> getAttributeValue(g, attribute),
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        );
    }

    /**
     * Gets the string value of the specified attribute from a gamer.
     */
    private String getAttributeValue(Gamer gamer, String attribute) {
        assert gamer != null : "Gamer should not be null when getting attribute value";
        switch (attribute) {
        case "name":
            return gamer.getName() == null ? null : gamer.getName().toString();
        case "gamertag":
            return gamer.getGamerTag() == null ? null : gamer.getGamerTag().toString();
        case "phone":
            return gamer.getPhone() == null ? null : gamer.getPhone().toString();
        case "email":
            return gamer.getEmail() == null ? null : gamer.getEmail().toString();
        case "group":
            return gamer.getGroup() == null ? null : gamer.getGroup().toString();
        case "server":
            return gamer.getServer() == null ? null : gamer.getServer().toString();
        case "country":
            return gamer.getCountry() == null ? null : gamer.getCountry().toString();
        case "region":
            return gamer.getRegion() == null ? null : gamer.getRegion().toString();
        case "note":
            return gamer.getNote() == null ? null : gamer.getNote().toString();
        default:
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return Objects.equals(attributes, otherSortCommand.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attributes", attributes)
                .toString();
    }
}
