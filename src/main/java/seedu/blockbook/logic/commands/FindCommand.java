package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Finds and lists all gamers in the BlockBook whose attributes match the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all gamers whose attributes contain any of "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers via Format 1."
            + "\n\tSpecific attribute search is also available via Format 2 and can be stacked.\n"
            + "\n\tFormat 1: " + COMMAND_WORD + " KEYWORD"
            + "\n\tExample: " + COMMAND_WORD + " joe\n"
            + "\n\tFormat 2: " + COMMAND_WORD + " [name/NAME] [phone/PHONE] [email/EMAIL] [group/GROUP]"
            + " [server/SERVER] [favourite/FAVOURITE] [country/COUNTRY] [region/REGION] [note/NOTE]"
            + "\n\tExample 1: " + COMMAND_WORD + " name/steve"
            + "\n\tExample 2: " + COMMAND_WORD + " name/steve gamertag/steve";

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
            return new CommandResult(
                    String.format(Messages.MESSAGE_GAMERS_FOUND_BY_FIND, filteredGamerCount));
        }
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
