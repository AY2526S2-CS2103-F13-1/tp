package seedu.blockbook.logic.commands;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Edits the name of an existing group in BlockBook.
 */
public class GroupEditCommand extends Command {

    public static final String COMMAND_WORD = "groupedit";
    public static final String COMMAND_ALIAS = "ge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_ALIAS + ")"
            + ": Edits an existing group's name in BlockBook.\n"
            + "Format: " + COMMAND_WORD + " BLOCKBOOK_GROUP_INDEX NEW_GROUP_NAME "
            + "or " + COMMAND_ALIAS + " BLOCKBOOK_GROUP_INDEX NEW_GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 I Love Alex Group";

    public static final String MESSAGE_SUCCESS = "Group: %1$s name edited to %2$s!";

    private static final Logger logger = LogsCenter.getLogger(GroupEditCommand.class);

    private final Index groupIndex;
    private final Group newGroup;

    /**
     * Creates a GroupEditCommand to edit the name of the specified group.
     * @param groupIndex the BLOCKBOOK_GROUP_INDEX for the group to be name edited
     * @param newGroup the group with the new group name edited in
     */
    public GroupEditCommand(Index groupIndex, Group newGroup) {
        requireNonNull(groupIndex);
        requireNonNull(newGroup);
        this.groupIndex = groupIndex;
        this.newGroup = newGroup; // Group with the new name payload
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> groupList = model.getGroupList();

        logger.info("Attempting to edit name of group index " + groupIndex.getOneBased() + " to " + newGroup);
        if (groupList.isEmpty() || groupIndex.getZeroBased() >= groupList.size()) {
            logger.warning("Edit name of group failed: index out of range");
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Group groupToRename = groupList.get(groupIndex.getZeroBased());
        if (model.hasGroup(newGroup)
                && !groupToRename.toString().equalsIgnoreCase(newGroup.toString())) {
            throw new CommandException(GroupCreateCommand.MESSAGE_DUPLICATE_GROUP);
        }

        List<Group> updatedGroups = new ArrayList<>(groupList);
        updatedGroups.set(groupIndex.getZeroBased(), newGroup);

        List<Gamer> updatedGamers = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            List<Group> gamerGroups = new ArrayList<>(gamer.getGroups());
            boolean updated = false;
            for (int i = 0; i < gamerGroups.size(); i++) {
                if (gamerGroups.get(i).equals(groupToRename)) {
                    gamerGroups.set(i, newGroup);
                    updated = true;
                }
            }
            if (updated) {
                updatedGamers.add(createUpdatedGamer(gamer, gamerGroups));
            } else {
                updatedGamers.add(gamer);
            }
        }

        BlockBook updatedBlockBook = new BlockBook();
        updatedBlockBook.setGroups(updatedGroups);
        updatedBlockBook.setGamers(updatedGamers);
        model.setBlockBook(updatedBlockBook);

        logger.info("Name edited group " + groupToRename + " to " + newGroup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupToRename, newGroup));
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
        if (!(other instanceof GroupEditCommand)) {
            return false;
        }
        GroupEditCommand otherCommand = (GroupEditCommand) other;
        return groupIndex.equals(otherCommand.groupIndex)
                && newGroup.equals(otherCommand.newGroup);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("newGroup", newGroup)
                .toString();
    }
}
