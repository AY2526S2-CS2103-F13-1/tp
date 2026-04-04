package seedu.blockbook.logic.parser;

import seedu.blockbook.logic.commands.ClearCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ClearCommand object.
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Returns a ClearCommand object with the given {@code String} for execution.
     *
     */
    public ClearCommand parse(String inputString) {
        return new ClearCommand(inputString);
    }
}
