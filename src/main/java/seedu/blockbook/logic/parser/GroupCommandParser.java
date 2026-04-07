package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.blockbook.logic.commands.Command;
import seedu.blockbook.logic.commands.GroupCreateCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Group;

/**
 * Parses input arguments and creates a new group-related command object.
 */
public class GroupCommandParser implements Parser<Command> {

    @Override
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCreateCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+", 2);
        String subcommand = parts[0].toLowerCase();
        if (!GroupCreateCommand.SUBCOMMAND_CREATE.equals(subcommand)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCreateCommand.MESSAGE_USAGE));
        }

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCreateCommand.MESSAGE_USAGE));
        }

        Group group = ParserUtil.parseGroup(parts[1]);
        return new GroupCreateCommand(group);
    }
}
