package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ConfirmationCodeUtil;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Removes a group from BlockBook and removes the group from all gamers.
 */
public class GroupNukeCommand extends Command {

    public static final String COMMAND_WORD = "groupnuke";
    public static final String COMMAND_ALIAS = "gn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_ALIAS + ")"
            + ": Removes a group from BlockBook and all gamers associated with it.\n"
            + "Format: " + COMMAND_WORD + " BLOCKBOOK_GROUP_INDEX or " + COMMAND_ALIAS + " BLOCKBOOK_GROUP_INDEX\n\n"
            + "Example: " + COMMAND_WORD + " 1\n";

    public static final String CONFIRMATION_MESSAGE_PREFIX = "----- WARNING! -----\n"
            + "THIS COMMAND WILL REMOVE A GROUP FROM BLOCKBOOK!!!\n"
            + "THIS COMMAND WILL ALSO REMOVE ALL GAMERS FROM THE GROUP!!!\n"
            + "THIS OPERATION CANNOT BE UNDONE!!!\n"
            + "Group to remove: %1$s\n"
            + "Gamertags affected: %2$s\n"
            + "If you wish to continue, enter the command below.\n"
            + COMMAND_WORD + " ";

    public static final String MESSAGE_SUCCESS =
            "Group: %1$s removed! All gamers have been removed from the group as well. "
                    + "Gamertags removed from group: %2$s";

    private static final Logger logger = LogsCenter.getLogger(GroupNukeCommand.class);
    private static String confirmationCode = null;
    private static Index pendingGroupIndex = null;
    private static String pendingGroupKey = null;

    private final Index groupIndex;
    private final String userInput;

    /**
     * Creates a GroupNukeCommand to remove the specified group and all gamers associated with it.
     *
     * @param groupIndex The BLOCKBOOK_GROUP_INDEX of the group to remove.
     * @param userInput  The user input which may contain the confirmation code.
     */
    public GroupNukeCommand(Index groupIndex, String userInput) {
        requireNonNull(groupIndex);
        this.groupIndex = groupIndex;
        this.userInput = userInput;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> groupList = model.getGroupList();

        logger.info("Attempting to nuke group index " + groupIndex.getOneBased());
        if (groupList.isEmpty() || groupIndex.getZeroBased() >= groupList.size()) {
            logger.warning("Nuke group failed: index out of range");
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Group groupToRemove = groupList.get(groupIndex.getZeroBased());

        List<String> affectedGamertags = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            if (gamer.getGroups().stream().anyMatch(group -> group.equals(groupToRemove))) {
                affectedGamertags.add(gamer.getGamerTag().toString());
            }
        }
        String affectedList = affectedGamertags.isEmpty() ? "None" : String.join(", ", affectedGamertags);

        String groupKey = groupToRemove.toString().toLowerCase(Locale.ROOT);

        if (confirmationCode == null || pendingGroupIndex == null || pendingGroupKey == null
                || !pendingGroupIndex.equals(groupIndex)
                || !pendingGroupKey.equals(groupKey)
                || !confirmationCode.equals(userInput)) {
            confirmationCode = ConfirmationCodeUtil.generateConfirmationCode();
            pendingGroupIndex = groupIndex;
            pendingGroupKey = groupKey; // Makes sure that it is the exact group being deleted
            String confirmationPrompt = String.format(CONFIRMATION_MESSAGE_PREFIX,
                    groupToRemove, affectedList)
                    + groupIndex.getOneBased() + confirmationCode;
            throw new CommandException(confirmationPrompt);
        }

        confirmationCode = null;
        pendingGroupIndex = null;
        pendingGroupKey = null;
        List<Gamer> gamers = new ArrayList<>(model.getBlockBook().getGamerList());
        List<String> removedGamertags = new ArrayList<>();

        // Process and remove group from each gamer contact
        for (Gamer gamer : gamers) {
            List<Group> updatedGroups = new ArrayList<>(gamer.getGroups());
            boolean removed = updatedGroups.removeIf(group -> group.equals(groupToRemove));
            if (removed) {
                removedGamertags.add(gamer.getGamerTag().toString());
                Gamer updatedGamer = createUpdatedGamer(gamer, updatedGroups);
                model.setGamer(gamer, updatedGamer);
            }
        }

        model.removeGroup(groupToRemove);
        logger.info("Removed group " + groupToRemove + " from BlockBook");

        String removedList = removedGamertags.isEmpty() ? "None" : String.join(", ", removedGamertags);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupToRemove, removedList));
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
        if (!(other instanceof GroupNukeCommand)) {
            return false;
        }
        GroupNukeCommand otherCommand = (GroupNukeCommand) other;
        return groupIndex.equals(otherCommand.groupIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .toString();
    }
}
