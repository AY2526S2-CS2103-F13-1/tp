package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.GroupViewCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupViewCommand object.
 */
public class GroupViewCommandParser implements Parser<GroupViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupViewCommand
     * and returns a GroupViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupViewCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length != 1 || !parts[0].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupViewCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(parts[0]);
            return new GroupViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE, pe);
        }
    }
}
