package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Shows the gamers associated with a group using the group index.
 */
public class GroupViewCommand extends Command {

    public static final String COMMAND_WORD = "groupview";
    public static final String COMMAND_ALIAS = "gv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_ALIAS + ")"
            + ": Shows the gamertags associated with a group identified by the index number used in the "
            + "displayed group list.\n\n"
            + "Format: " + COMMAND_WORD + " BLOCKBOOK_GROUP_INDEX or " + COMMAND_ALIAS + " BLOCKBOOK_GROUP_INDEX\n\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Group: %1$s Gamertags: %2$s";
    public static final String MESSAGE_NO_GAMERS = "Group: %1$s has no associated gamers.";

    private final Index groupIndex;

    /**
     * Create a GroupViewCommand to view the gamers associated with a group.
     * @param groupIndex the BLOCKBOOK_GROUP_INDEX used to identify the group in the displayed group list.
     */
    public GroupViewCommand(Index groupIndex) {
        requireNonNull(groupIndex);
        this.groupIndex = groupIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> groupList = model.getGroupList();
        if (groupList.isEmpty() || groupIndex.getZeroBased() >= groupList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Group targetGroup = groupList.get(groupIndex.getZeroBased());
        model.updateFilteredGamerList(gamer -> gamerHasGroup(gamer, targetGroup));

        List<Gamer> filteredGamers = model.getFilteredGamerList();
        if (filteredGamers.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_GAMERS, targetGroup));
        }

        String gamertags = filteredGamers.stream()
                .map(gamer -> gamer.getGamerTag().toString())
                .collect(Collectors.joining(", "));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetGroup, gamertags));
    }

    private boolean gamerHasGroup(Gamer gamer, Group targetGroup) {
        if (gamer.getGroups() == null || gamer.getGroups().isEmpty()) {
            return false;
        }
        return gamer.getGroups().stream()
                .anyMatch(group -> group.toString().equalsIgnoreCase(targetGroup.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GroupViewCommand)) {
            return false;
        }
        GroupViewCommand otherCommand = (GroupViewCommand) other;
        return groupIndex.equals(otherCommand.groupIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .toString();
    }
}
