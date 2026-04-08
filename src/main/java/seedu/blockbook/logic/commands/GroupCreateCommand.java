package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Group;

/**
 * Creates a new group in BlockBook.
 */
public class GroupCreateCommand extends Command {

    public static final String COMMAND_WORD = "groupcreate";
    public static final String COMMAND_ALIAS = "gc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_ALIAS + ")"
            + ": Creates a new group in BlockBook.\n\n"
            + "Format: " + COMMAND_WORD + " GROUP" + " or " + COMMAND_ALIAS + " GROUP\n\n"
            + "Example: " + COMMAND_WORD + " iLoveSteve";

    public static final String MESSAGE_SUCCESS = "Group: %1$s added!";
    public static final String MESSAGE_DUPLICATE_GROUP =
            "This group already exists! Please use another group name!";
    private static final Logger logger = LogsCenter.getLogger(GroupCreateCommand.class);

    private final Group group;

    /**
     * Creates a GroupCreateCommand to add the specified group.
     */
    public GroupCreateCommand(Group group) {
        requireNonNull(group);
        this.group = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Attempting to add group: " + group);
        if (model.hasGroup(group)) {
            logger.warning("Duplicate group detected: " + group);
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        model.addGroup(group);
        logger.info("Group added successfully: " + group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, group));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCreateCommand)) {
            return false;
        }

        GroupCreateCommand otherCommand = (GroupCreateCommand) other;
        return group.equals(otherCommand.group);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("group", group)
                .toString();
    }
}
