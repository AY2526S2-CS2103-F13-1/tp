package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamertagContainsKeywordPredicate;

/**
 * Finds and lists the full details of the gamer contact specified via Gamertag
 * param. Keyword matching is case insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the full details of gamers whose gamertag is"
            + " the specified keyword (case-insensitive) and displays them in the command prompt.\n"
            + "Format: view gamertag/GAMERTAG ";

    private final GamertagContainsKeywordPredicate predicate;

    public ViewCommand(GamertagContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGamerList(predicate);
        int gamerListSize = model.getFilteredGamerList().size();
        if (gamerListSize == 0) {
            return new CommandResult(Messages.MESSAGE_GAMERTAG_NOT_FOUND);
        } else {
            Gamer specifiedGamer = model.getFilteredGamerList().get(0);
            String formattedContact = "Name: " + Messages.formatNullable(specifiedGamer.getName())
                    + " Gamertag: " + specifiedGamer.getGamerTag()
                    + " Phone: " + Messages.formatNullable(specifiedGamer.getPhone())
                    + " Email: " + Messages.formatNullable(specifiedGamer.getEmail())
                    + " Group: " + Messages.formatNullable(specifiedGamer.getGroup())
                    + " Server: " + Messages.formatNullable(specifiedGamer.getServer())
                    + " Favourite: " + Messages.formatNullable(specifiedGamer.getFavourite())
                    + " Country: " + Messages.formatNullable(specifiedGamer.getCountry())
                    + " Region: " + Messages.formatNullable(specifiedGamer.getRegion())
                    + " Note: " + Messages.formatNullable(specifiedGamer.getNote());
            return new CommandResult(formattedContact);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
