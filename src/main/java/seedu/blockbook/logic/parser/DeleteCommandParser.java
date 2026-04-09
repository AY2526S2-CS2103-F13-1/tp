package seedu.blockbook.logic.parser;

import java.util.ArrayList;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.DeleteCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            ArrayList<Index> indexes = ParserUtil.parseMultipleIndexes(args);
            return new DeleteCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_MULTIPLE_INDEXES_INVALID, pe);
        }
    }

}

