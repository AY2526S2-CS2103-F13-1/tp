package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.SpecificAttributesMatchPredicate;

/**
 * Finds and lists all gamers in the BlockBook whose attributes match the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String COMMAND_WORD_WITH_ALIAS = "(f)ind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all gamers whose attributes contain any of "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers via Format 1."
            + "\nFormat 1: "+ COMMAND_WORD_WITH_ALIAS + " KEYWORD\n"
            + "\nFormat 1 example: " + COMMAND_WORD + " joe\n"
            + "\nSpecific attribute search is also available via Format 2 and can be stacked."
            + "\nFormat 2: " + COMMAND_WORD_WITH_ALIAS + " [name/NAME] [phone/PHONE] [email/EMAIL] [group/GROUP]"
            + " [server/SERVER] [favourite/FAVOURITE] [country/COUNTRY] [region/REGION] [note/NOTE]\n"
            + "\nFormat 2 example 1: " + COMMAND_WORD + " name/steve"
            + "\nFormat 2 example 2: " + COMMAND_WORD + " name/steve gamertag/steve";

    // Use the generic Predicate interface to accept ANY valid gamer predicate
    private final Predicate<Gamer> predicate;

    public FindCommand(Predicate<Gamer> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGamerList(predicate);
        int filteredGamerCount = model.getFilteredGamerList().size();
        if (filteredGamerCount == 0) {
            return new CommandResult(Messages.MESSAGE_NO_GAMERS_FOUND_BY_FIND);
        } else {
            return new CommandResult(buildFoundMessage(filteredGamerCount));
        }
    }

    private String buildFoundMessage(int filteredGamerCount) {
        if (predicate instanceof AnyAttributeContainsKeywordsPredicate) {
            return String.format(Messages.MESSAGE_GAMERS_FOUND_BY_FIND, filteredGamerCount);
        }
        if (predicate instanceof SpecificAttributesMatchPredicate) {
            List<String> labels = ((SpecificAttributesMatchPredicate) predicate).getSearchCriteriaLabels();
            String criteria = labels.isEmpty() ? "Unknown" : String.join(", ", labels);
            return String.format(Messages.MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC, filteredGamerCount, criteria);
        }
        return String.format(Messages.MESSAGE_GAMERS_FOUND_BY_FIND, filteredGamerCount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
