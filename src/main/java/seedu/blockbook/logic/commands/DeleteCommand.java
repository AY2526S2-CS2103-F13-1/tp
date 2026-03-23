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
 * Deletes a gamer identified using its displayed index from the BlockBook.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the gamer contacts identified by the index numbers used in the displayed gamer list.\n"
            + "Parameters: INDEX (must be a positive integer) [INDEX] ...\n"
            + "Example: " + COMMAND_WORD + " 1, " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_GAMER_SUCCESS = "Contact(s) deleted: %1$s";

    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> indexList) {
        this.targetIndexes = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredGamerList();

        // Reverse sort list to ensure deleting from the back of the list to preserve index validity
        targetIndexes.sort((o1, o2) -> o2.getOneBased() - o1.getOneBased());

        validateDeleteIndex(lastShownList);

        int lastIndex = -1;
        String deletedGamerNames = "";
        for (Index index : targetIndexes) {
            int indexNumber = index.getZeroBased();
            if (lastIndex == indexNumber) {
                continue;
            }
            lastIndex = indexNumber;
            assert indexNumber < lastShownList.size();

            Gamer gamerToDelete = lastShownList.get(indexNumber);
            model.deleteGamer(gamerToDelete);
            deletedGamerNames = gamerToDelete.getGamerTag() + ", " + deletedGamerNames;
        }
        deletedGamerNames = deletedGamerNames.substring(0, deletedGamerNames.length() - 2);;

        return new CommandResult(String.format(MESSAGE_DELETE_GAMER_SUCCESS, deletedGamerNames));
    }

    /**
     * Validates whether the target index refers to a valid gamer in the given list.
     *
     * @param gamerList The currently displayed list of gamers.
     * @throws CommandException If the list is empty or if the index is out of range.
     */
    private void validateDeleteIndex(List<Gamer> gamerList) throws CommandException {
        if (gamerList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CONTACT_LIST);
        }
        for  (Index index : targetIndexes) {
            if (index.getZeroBased() >= gamerList.size()) {
                throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndexes.equals(otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndexes)
                .toString();
    }
}


