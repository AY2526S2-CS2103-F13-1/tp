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
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers via Format 1.\n"
            + "Specific attribute search is also available via Format 2 and can be stacked. \n"
            + "Format 1: find KEYWORD\n"
            + "Example: " + COMMAND_WORD + " joe\n"
            + "Format 2: find ATTRIBUTE1/KEYWORD1 [ATRRIBUTE2/KEYWORD2]... \n"
            + "find name/NAME\nfind phone/PHONE\nfind email/EMAIL\n"
            + "find group/GROUP\nfind server/SERVER\nfind favourite/\n"
            + "find country/COUNTRY\nfind region/REGION\nfind note/NOTE\n"
            + "Example: find name/steve\n"
            + "Example: find name/steve gamertag/steve \n";

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
