package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Finds and lists the full details of the gamer contact specified via index.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the full details of the gamer contact "
            + "identified by the index number used in the displayed gamer list.\n"
            + "Format: " + COMMAND_WORD + " INDEX";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredGamerList();
        int index = targetIndex.getZeroBased();
        if (index < 0 || index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Gamer specifiedGamer = lastShownList.get(index);
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
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
