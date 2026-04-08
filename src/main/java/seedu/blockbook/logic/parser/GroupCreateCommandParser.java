package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.logic.commands.GroupCreateCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Group;

/**
 * Parses input arguments and creates a new GroupCreateCommand object.
 */
public class GroupCreateCommandParser implements Parser<GroupCreateCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a GroupCreateCommand.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCreateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCreateCommand.MESSAGE_USAGE));
        }

        Group group = ParserUtil.parseGroup(trimmedArgs);
        return new GroupCreateCommand(group);
    }
}
