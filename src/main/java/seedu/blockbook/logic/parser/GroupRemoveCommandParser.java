package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.GroupRemoveCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupRemoveCommand object.
 */
public class GroupRemoveCommandParser implements Parser<GroupRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupRemoveCommand
     * and returns a GroupRemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupRemoveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupRemoveCommand.MESSAGE_USAGE));
        }
        if (!parts[0].matches("-?\\d+") || !parts[1].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupRemoveCommand.MESSAGE_USAGE));
        }

        int gamerIndexValue = Integer.parseInt(parts[0]);
        int groupIndexValue = Integer.parseInt(parts[1]);
        if (gamerIndexValue <= 0 || groupIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Index gamerIndex = Index.fromOneBased(gamerIndexValue);
        Index groupIndex = Index.fromOneBased(groupIndexValue);
        return new GroupRemoveCommand(gamerIndex, groupIndex);
    }
}
