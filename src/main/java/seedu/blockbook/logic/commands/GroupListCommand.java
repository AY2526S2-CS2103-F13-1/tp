package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Group;

/**
 * Lists all groups in BlockBook.
 */
public class GroupListCommand extends Command {

    public static final String COMMAND_WORD = "grouplist";
    public static final String COMMAND_ALIAS = "gl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_ALIAS + ")"
            + ": Lists all groups in your BlockBook.\n"
            + "\nFormat: " + COMMAND_WORD + " or " + COMMAND_ALIAS;

    public static final String MESSAGE_NO_GROUPS = Messages.MESSAGE_GROUPS_LISTED_NO_GROUP;
    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_GROUPS_LISTED_OVERVIEW;
    private static final Logger logger = LogsCenter.getLogger(GroupListCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Listing all groups.");
        List<Group> groups = model.getGroupList();
        if (groups.isEmpty()) {
            logger.info("No groups found.");
            return new CommandResult(MESSAGE_NO_GROUPS);
        }

        String groupList = IntStream.range(0, groups.size())
                .mapToObj(i -> (i + 1) + ". " + groups.get(i))
                .collect(Collectors.joining(", "));
        logger.info("Listed " + groups.size() + " group(s).");
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupList));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof GroupListCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
