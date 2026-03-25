package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;

import java.util.stream.Stream;

import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        validateRequiredPrefixes(argMultimap);
        verifyNoDuplicatePrefixes(argMultimap);

        Gamer gamer = buildGamer(argMultimap);
        return new AddCommand(gamer);
    }

    /**
     * Tokenizes the given add command arguments using the supported prefixes.
     *
     * @param args Raw add command arguments.
     * @return Tokenized argument multimap.
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args,
                PREFIX_GAMERTAG,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_GROUP,
                PREFIX_SERVER,
                PREFIX_FAVOURITE,
                PREFIX_COUNTRY,
                PREFIX_REGION,
                PREFIX_NOTE);
    }

    /**
     * Validates that all required prefixes are present and the preamble is empty.
     *
     * @param argMultimap Tokenized argument multimap.
     * @throws ParseException If the add command format is invalid.
     */
    private void validateRequiredPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GAMERTAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates that no single-valued prefixes are repeated.
     *
     * @param argMultimap Tokenized argument multimap.
     * @throws ParseException If duplicate prefixes are found.
     */
    private void verifyNoDuplicatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_GAMERTAG,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_GROUP,
                PREFIX_SERVER,
                PREFIX_FAVOURITE,
                PREFIX_COUNTRY,
                PREFIX_REGION,
                PREFIX_NOTE
        );
    }

    /**
     * Builds a gamer from the parsed add command arguments.
     *
     * @param argMultimap Tokenized argument multimap.
     * @return Gamer built from the provided arguments.
     * @throws ParseException If any field value is invalid.
     */
    private Gamer buildGamer(ArgumentMultimap argMultimap) throws ParseException {
        // Required
        GamerTag gamerTag = ParserUtil.parseGamerTag(argMultimap.getValue(PREFIX_GAMERTAG).get());

        // Optional
        Name name = parseOptionalName(argMultimap);
        Phone phone = parseOptionalPhone(argMultimap);
        Email email = parseOptionalEmail(argMultimap);
        Group group = parseOptionalGroup(argMultimap);
        Server server = parseOptionalServer(argMultimap);
        Favourite favourite = parseOptionalFavourite(argMultimap);
        Country country = parseOptionalCountry(argMultimap);
        Region region = parseOptionalRegion(argMultimap);
        Note note = parseOptionalNote(argMultimap);

        Gamer gamer = new Gamer(
                name,
                gamerTag,
                phone,
                email,
                group,
                server,
                favourite,
                country,
                region,
                note
        );
        return gamer;
    }

    private Name parseOptionalName(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }
        return null;
    }

    private Phone parseOptionalPhone(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }
        return null;
    }

    private Email parseOptionalEmail(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        return null;
    }

    private Group parseOptionalGroup(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
            return ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        }
        return null;
    }

    private Server parseOptionalServer(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_SERVER).isPresent()) {
            return ParserUtil.parseServer(argMultimap.getValue(PREFIX_SERVER).get());
        }
        return null;
    }

    private Favourite parseOptionalFavourite(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_FAVOURITE).isPresent()) {
            return ParserUtil.parseFavourite(argMultimap.getValue(PREFIX_FAVOURITE).get());
        }
        return new Favourite("unfav");
    }

    private Country parseOptionalCountry(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_COUNTRY).isPresent()) {
            return ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get());
        }
        return null;
    }

    private Region parseOptionalRegion(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            return ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get());
        }
        return null;
    }

    private Note parseOptionalNote(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            return ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
