package seedu.blockbook.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.FavouriteCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavouriteCommand object.
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {
    private static final Logger logger = LogsCenter.getLogger(FavouriteCommandParser.class);

    private final boolean markFavourite;

    public FavouriteCommandParser(boolean markFavourite) {
        this.markFavourite = markFavourite;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns a FavouriteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.fine("Parsed " + (markFavourite ? "favourite" : "unfavourite")
                    + " command for index " + index.getOneBased() + ".");
            return new FavouriteCommand(index, markFavourite);
        } catch (ParseException pe) {
            if (trimmedArgs.matches("-?\\d+")) {
                logger.finer("Favourite parse failed: index out of range (" + trimmedArgs + ").");
                throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE, pe);
            }
            logger.finer("Favourite parse failed: invalid arguments (" + trimmedArgs + ").");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE), pe);
        }
    }
}
