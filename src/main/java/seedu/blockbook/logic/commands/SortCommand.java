package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Sorts the contact list in BlockBook by the specified attributes.
 * Favourite contacts are always shown first.
 * Sorting is session-based and does not persist to storage.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts by the specified attributes. "
            + "Favourite contacts are always shown first.\n"
            + "Parameters: [ATTRIBUTE/]...\n"
            + "Example: " + COMMAND_WORD + " name/ phone/";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all contacts.";
    public static final String MESSAGE_EMPTY_LIST = "There are no contacts to sort!";
    public static final String MESSAGE_INVALID_ATTRIBUTES =
            "Please ensure all attributes are valid. "
            + "Possible attributes: name, phone, email, group, server, favourite, country, region, note";

    public static final List<String> VALID_ATTRIBUTES = List.of(
            "name", "phone", "email", "group", "server", "favourite", "country", "region", "note", "gamertag"
    );

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

        if (model.getFilteredGamerList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // Validate attributes
        for (String attr : attributes) {
            if (!VALID_ATTRIBUTES.contains(attr)) {
                throw new CommandException(MESSAGE_INVALID_ATTRIBUTES);
            }
        }

        // Build comparator: favourites first, then by specified attributes
        Comparator<Gamer> comparator = buildComparator();

        model.sortGamerList(comparator);

        return new CommandResult(MESSAGE_SORT_SUCCESS);
    }

    /**
     * Builds a comparator that sorts favourites first, then by the specified attributes.
     */
    private Comparator<Gamer> buildComparator() {
        // Favourites always come first
        Comparator<Gamer> comparator = Comparator.comparing((Gamer g) -> {
            if (g.getFavourite() == null) {
                return 1; // non-favourite (null) goes after
            }
            return g.getFavourite().toString().equalsIgnoreCase("Yes") ? 0 : 1;
        });

        List<String> sortAttributes = attributes.isEmpty()
                ? List.of("gamertag") : attributes;

        for (String attr : sortAttributes) {
            comparator = comparator.thenComparing(getAttributeComparator(attr));
        }

        return comparator;
    }

    /**
     * Returns a comparator for the given attribute name.
     * Null values are sorted to the end.
     */
    private Comparator<Gamer> getAttributeComparator(String attribute) {
        return Comparator.comparing((Gamer g) -> getAttributeValue(g, attribute),
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        );
    }

    /**
     * Gets the string value of the specified attribute from a gamer.
     */
    private String getAttributeValue(Gamer gamer, String attribute) {
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
        case "favourite":
            return gamer.getFavourite() == null ? null : gamer.getFavourite().toString();
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
