package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Removes a gamer from a group using displayed indexes.
 */
public class GroupRemoveCommand extends Command {

    public static final String COMMAND_WORD = "groupremove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a gamer from a group using the displayed indexes.\n"
            + "Format: " + COMMAND_WORD + " GAMER_INDEX GROUP_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SUCCESS = "Gamertag: %1$s removed from Group: %2$s !";

    private static final Logger logger = LogsCenter.getLogger(GroupRemoveCommand.class);

    private final Index gamerIndex;
    private final Index groupIndex;

    /**
     * Creates a GroupRemoveCommand to remoeve the specified gamer from a specified group.
     * @param gamerIndex Index of the gamer contact relative to the list of gamer contacts
     * @param groupIndex Index of the group relative to the groups array associated to the gamer
     */
    public GroupRemoveCommand(Index gamerIndex, Index groupIndex) {
        requireNonNull(gamerIndex);
        requireNonNull(groupIndex);
        this.gamerIndex = gamerIndex;
        this.groupIndex = groupIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredGamerList();

        logger.info("Attempting to remove group index " + groupIndex.getOneBased()
                + " from gamer index " + gamerIndex.getOneBased());
        validateGamerIndex(lastShownList);

        Gamer gamerToEdit = lastShownList.get(gamerIndex.getZeroBased());
        List<Group> updatedGroups = new ArrayList<>(gamerToEdit.getGroups());
        if (groupIndex.getZeroBased() >= updatedGroups.size()) {
            logger.warning("Remove group failed: group index out of range for gamer "
                    + gamerToEdit.getGamerTag());
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Group groupToRemove = updatedGroups.remove(groupIndex.getZeroBased());

        Gamer updatedGamer = createUpdatedGamer(gamerToEdit, updatedGroups);
        model.setGamer(gamerToEdit, updatedGamer);

        logger.info("Removed gamer " + gamerToEdit.getGamerTag() + " from group " + groupToRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                gamerToEdit.getGamerTag(), groupToRemove));
    }

    private void validateGamerIndex(List<Gamer> gamerList) throws CommandException {
        if (gamerList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CONTACT_LIST);
        }
        if (gamerIndex.getZeroBased() >= gamerList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
    }

    private static Gamer createUpdatedGamer(Gamer gamerToEdit, List<Group> updatedGroups) {
        requireNonNull(gamerToEdit);
        requireNonNull(updatedGroups);

        return new Gamer(
                gamerToEdit.getName(),
                gamerToEdit.getGamerTag(),
                gamerToEdit.getPhone(),
                gamerToEdit.getEmail(),
                updatedGroups,
                gamerToEdit.getServer(),
                gamerToEdit.getFavourite(),
                gamerToEdit.getCountry(),
                gamerToEdit.getRegion(),
                gamerToEdit.getNote()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GroupRemoveCommand)) {
            return false;
        }
        GroupRemoveCommand otherCommand = (GroupRemoveCommand) other;
        return gamerIndex.equals(otherCommand.gamerIndex)
                && groupIndex.equals(otherCommand.groupIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("gamerIndex", gamerIndex)
                .add("groupIndex", groupIndex)
                .toString();
    }
}
