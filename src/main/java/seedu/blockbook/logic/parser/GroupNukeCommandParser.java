package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.GroupNukeCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupNukeCommand object.
 */
public class GroupNukeCommandParser implements Parser<GroupNukeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupNukeCommand
     * and returns a GroupNukeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupNukeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupNukeCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length < 1 || parts.length > 2 || !parts[0].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupNukeCommand.MESSAGE_USAGE));
        }

        final int groupIndexValue;
        try {
            groupIndexValue = Integer.parseInt(parts[0]);
        } catch (NumberFormatException nfe) {
            throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
        if (groupIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Index groupIndex = Index.fromOneBased(groupIndexValue);
        String confirmationInput = parts.length == 2 ? " " + parts[1] : "";
        return new GroupNukeCommand(groupIndex, confirmationInput);
    }
}
