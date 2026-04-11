package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.GroupAddCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupAddCommand object.
 */
public class GroupAddCommandParser implements Parser<GroupAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupAddCommand
     * and returns a GroupAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupAddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupAddCommand.MESSAGE_USAGE));
        }
        if (!parts[0].matches("-?\\d+") || !parts[1].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupAddCommand.MESSAGE_USAGE));
        }

        // Handle integer overflow
        int gamerIndexValue;
        int groupIndexValue;
        try {
            gamerIndexValue = Integer.parseInt(parts[0]);
        } catch (NumberFormatException nfe) {
            throw new ParseException(Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
        }
        try {
            groupIndexValue = Integer.parseInt(parts[1]);
        } catch (NumberFormatException nfe) {
            throw new ParseException(Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        }
        if (gamerIndexValue <= 0 && groupIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE + "\n"
                    + Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        }
        if (gamerIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
        }
        if (groupIndexValue <= 0) {
            throw new ParseException(Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        }

        Index gamerIndex = Index.fromOneBased(gamerIndexValue);
        Index groupIndex = Index.fromOneBased(groupIndexValue);
        return new GroupAddCommand(gamerIndex, groupIndex);
    }
}
