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
 * Adds a gamer to a group using displayed indexes.
 */
public class GroupAddCommand extends Command {

    public static final String COMMAND_WORD = "groupadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a gamer to a group using the displayed indexes.\n"
            + "Format: " + COMMAND_WORD + " GAMER_INDEX GROUP_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SUCCESS = "Gamertag: %1$s added to Group: %2$s.";
    public static final String MESSAGE_ALREADY_IN_GROUP = "This gamer contact is already in the group!";

    private static final Logger logger = LogsCenter.getLogger(GroupAddCommand.class);

    private final Index gamerIndex;
    private final Index groupIndex;

    /**
     * Creates a GroupAddCommand to add the specified gamer to the specified group.
     * @param gamerIndex Index of the gamer contact relative to the list of gamer contacts
     * @param groupIndex Index of the group relative to what is saved in contact.json's blockbookgroups key.
     */
    public GroupAddCommand(Index gamerIndex, Index groupIndex) {
        requireNonNull(gamerIndex);
        requireNonNull(groupIndex);
        this.gamerIndex = gamerIndex;
        this.groupIndex = groupIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredGamerList();
        List<Group> groupList = model.getGroupList();

        logger.info("Attempting to add gamer at index " + gamerIndex.getOneBased()
                + " to group index " + groupIndex.getOneBased());
        validateIndexes(lastShownList, groupList);

        Gamer gamerToEdit = lastShownList.get(gamerIndex.getZeroBased());
        Group groupToAdd = groupList.get(groupIndex.getZeroBased());

        List<Group> updatedGroups = new ArrayList<>(gamerToEdit.getGroups());
        if (updatedGroups.contains(groupToAdd)) {
            logger.warning("Add group failed: gamer already in group " + groupToAdd);
            throw new CommandException(MESSAGE_ALREADY_IN_GROUP);
        }

        updatedGroups.add(groupToAdd);
        Gamer updatedGamer = createUpdatedGamer(gamerToEdit, updatedGroups);
        model.setGamer(gamerToEdit, updatedGamer);

        logger.info("Added gamer " + gamerToEdit.getGamerTag() + " to group " + groupToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                gamerToEdit.getGamerTag(), groupToAdd));
    }

    private void validateIndexes(List<Gamer> gamerList, List<Group> groupList) throws CommandException {
        if (gamerList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CONTACT_LIST);
        }
        if (gamerIndex.getZeroBased() >= gamerList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
        if (groupList.isEmpty() || groupIndex.getZeroBased() >= groupList.size()) {
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
        if (!(other instanceof GroupAddCommand)) {
            return false;
        }
        GroupAddCommand otherCommand = (GroupAddCommand) other;
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
