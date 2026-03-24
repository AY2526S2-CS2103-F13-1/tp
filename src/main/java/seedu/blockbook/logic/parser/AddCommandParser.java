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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
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

        if (!arePrefixesPresent(argMultimap, PREFIX_GAMERTAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_GAMERTAG,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_SERVER,
                PREFIX_FAVOURITE,
                PREFIX_COUNTRY,
                PREFIX_REGION,
                PREFIX_NOTE
        );

        GamerTag gamerTag = ParserUtil.parseGamerTag(
                argMultimap.getValue(PREFIX_GAMERTAG).get());

        // Optional
        Name name = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        Phone phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        Email email = null;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }

        Group group = null;
        if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
            group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        }

        Server server = null;
        if (argMultimap.getValue(PREFIX_SERVER).isPresent()) {
            server = ParserUtil.parseServer(argMultimap.getValue(PREFIX_SERVER).get());
        }

        Favourite favourite = new Favourite("unfav");
        if (argMultimap.getValue(PREFIX_FAVOURITE).isPresent()) {
            favourite = ParserUtil.parseFavourite(argMultimap.getValue(PREFIX_FAVOURITE).get());
        }

        Country country = null;
        if (argMultimap.getValue(PREFIX_COUNTRY).isPresent()) {
            country = ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get());
        }

        Region region = null;
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            region = ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get());
        }

        Note note = null;
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        }


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

        return new AddCommand(gamer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
