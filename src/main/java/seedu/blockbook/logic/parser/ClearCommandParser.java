package seedu.blockbook.logic.parser;

import seedu.blockbook.logic.commands.ClearCommand;

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
