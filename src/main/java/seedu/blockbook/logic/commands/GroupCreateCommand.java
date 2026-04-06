package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Group;

/**
 * Creates a new group in BlockBook.
 */
public class GroupCreateCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String SUBCOMMAND_CREATE = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new group.\n\n"
            + "Format: " + COMMAND_WORD + " " + SUBCOMMAND_CREATE + " GROUP\n\n"
            + "Example: " + COMMAND_WORD + " " + SUBCOMMAND_CREATE + " Raid Team";

    public static final String MESSAGE_SUCCESS = "Group: %1$s added!";
    public static final String MESSAGE_DUPLICATE_GROUP =
            "This group already exists! Please use another group name!";

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
        if (model.hasGroup(group)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        model.addGroup(group);
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
