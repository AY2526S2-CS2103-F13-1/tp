package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Sorts the contact list in BlockBook by the specified attributes.
 * Sorting is session-based and does not persist to storage.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";
    public static final String COMMAND_WORD_WITH_ALIAS = "(s)ort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts by the specified attributes.\n"
            + "\nIf no attributes are specified, results are sorted by gamertag by default.\n"
            + "\nFormat: " + COMMAND_WORD_WITH_ALIAS
            + " [(g)amertag/] [(n)ame/] [(p)hone/] [(e)mail/] [(gr)oup/]"
            + " [(s)erver/] [(fav)ourite/] [(c)ountry/] [(r)egion/] [note/]\n"
            + "\nExample: " + COMMAND_WORD + " phone/ gamertag/";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all contacts by %1$s.";
    public static final String MESSAGE_SORT_DEFAULT_SUCCESS = "Sorted all contacts by gamertag (default).";
    public static final String MESSAGE_EMPTY_LIST = "There are no contacts to sort!";

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
        this.attributes = List.copyOf(attributes);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing sort command with attributes: " + attributes);

        if (model.getFilteredGamerList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        Comparator<Gamer> comparator = buildComparator();

        model.sortGamerList(comparator);

        String successMessage = attributes.isEmpty()
                ? MESSAGE_SORT_DEFAULT_SUCCESS
                : String.format(MESSAGE_SORT_SUCCESS, String.join(", ", attributes));
        logger.info("Sort command executed successfully");
        return new CommandResult(successMessage);
    }

    /**
     * Builds a comparator from the specified sort attributes.
     */
    private Comparator<Gamer> buildComparator() {
        List<String> sortAttributes = attributes.isEmpty()
                ? List.of("gamertag") : attributes;

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
        if ("favourite".equals(attribute)) {
            // Explicit favourite sorting places favourites before non-favourites.
            return Comparator.comparing((Gamer g) -> g.getFavourite() == null ? null : g.getFavourite().isFav(),
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
            return getGroupSortKey(gamer.getGroups());
        case "server":
            return gamer.getServer() == null ? null : gamer.getServer().toString();
        case "country":
            return gamer.getCountry() == null ? null : gamer.getCountry().toString();
        case "region":
            return gamer.getRegion() == null ? null : gamer.getRegion().toString();
        case "note":
            return gamer.getNote() == null ? null : gamer.getNote().toString();
        default:
            throw new IllegalArgumentException("Unsupported sort attribute: " + attribute);
        }
    }

    private String getGroupSortKey(List<Group> groups) {
        if (groups == null || groups.isEmpty()) {
            return null;
        }
        return groups.stream()
                .map(Group::toString)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.joining(", "));
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
