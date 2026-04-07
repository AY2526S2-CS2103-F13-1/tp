package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.GroupEditCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Group;

/**
 * Parses input arguments and creates a new GroupEditCommand object.
 */
public class GroupEditCommandParser implements Parser<GroupEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupEditCommand
     * and returns a GroupEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupEditCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupEditCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+", 2);
        if (parts.length < 2 || !parts[0].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupEditCommand.MESSAGE_USAGE));
        }

        int groupIndexValue = Integer.parseInt(parts[0]);
        if (groupIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Index groupIndex = Index.fromOneBased(groupIndexValue);
        // Group with the new name payload
        Group newGroup = ParserUtil.parseGroup(parts[1]);
        return new GroupEditCommand(groupIndex, newGroup);
    }
}
