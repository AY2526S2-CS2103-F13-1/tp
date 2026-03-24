package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;

import java.util.stream.Stream;

import seedu.blockbook.logic.commands.ViewCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.GamertagContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ViewCommand and returns a ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_GAMERTAG
                );
        if (!arePrefixesPresent(argMultimap, PREFIX_GAMERTAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_GAMERTAG
        );

        GamerTag gamerTag = ParserUtil.parseGamerTag(
                argMultimap.getValue(PREFIX_GAMERTAG).get());

        return new ViewCommand(new GamertagContainsKeywordPredicate(gamerTag.toString()));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional}
     * values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
