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

import seedu.blockbook.logic.commands.FindCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;
import seedu.blockbook.model.gamer.SpecificAttributesMatchPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GAMERTAG, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_GROUP, PREFIX_SERVER, PREFIX_FAVOURITE, PREFIX_COUNTRY,
                PREFIX_REGION, PREFIX_NOTE);

        // Trim out prefixes to get the actual length of the keyword.
        boolean hasAnyPrefixedArguments =
                argMultimap.getValue(PREFIX_GAMERTAG).isPresent()
                        || argMultimap.getValue(PREFIX_NAME).isPresent()
                        || argMultimap.getValue(PREFIX_PHONE).isPresent()
                        || argMultimap.getValue(PREFIX_EMAIL).isPresent()
                        || argMultimap.getValue(PREFIX_GROUP).isPresent()
                        || argMultimap.getValue(PREFIX_SERVER).isPresent()
                        || argMultimap.getValue(PREFIX_FAVOURITE).isPresent()
                        || argMultimap.getValue(PREFIX_COUNTRY).isPresent()
                        || argMultimap.getValue(PREFIX_REGION).isPresent()
                        || argMultimap.getValue(PREFIX_NOTE).isPresent();

        String preamble = argMultimap.getPreamble().trim();
        // Prevent mixed formats
        if (hasAnyPrefixedArguments && !preamble.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!hasAnyPrefixedArguments) {
            if (preamble.length() > 50) {
                throw new ParseException("Global search KEYWORD input cannot exceed 50 characters.");
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_GAMERTAG, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_GROUP,
                PREFIX_SERVER, PREFIX_FAVOURITE, PREFIX_COUNTRY, PREFIX_REGION, PREFIX_NOTE
        );

        // Extract and VALIDATE the specific arguments using our helper method
        String nameArg = extractAndValidateArg(argMultimap, PREFIX_NAME);
        if (nameArg != null && !Name.isValidName(nameArg)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        String gamertagArg = extractAndValidateArg(argMultimap, PREFIX_GAMERTAG);
        if (gamertagArg != null && !GamerTag.isValidGamerTag(gamertagArg)) {
            throw new ParseException(GamerTag.MESSAGE_CONSTRAINTS);
        }

        String phoneArg = extractAndValidateArg(argMultimap, PREFIX_PHONE);
        // Lax validation is added here to allow for Partial Search
        if (phoneArg != null && !Phone.isValidLaxPhone(phoneArg)) {
            throw new ParseException(Phone.MESSAGE_LAX_CONSTRAINTS);
        }

        String emailArg = extractAndValidateArg(argMultimap, PREFIX_EMAIL);
        // Lax validation is added here to allow for Partial Search
        if (emailArg != null && !Email.isValidLaxEmail(emailArg)) {
            throw new ParseException(Email.MESSAGE_LAX_CONSTRAINTS);
        }

        String groupArg = extractAndValidateArg(argMultimap, PREFIX_GROUP);
        if (groupArg != null && !Group.isValidGroup(groupArg)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }

        String serverArg = extractAndValidateArg(argMultimap, PREFIX_SERVER);
        if (serverArg != null && !Server.isValidServer(serverArg)) {
            throw new ParseException(Server.MESSAGE_CONSTRAINTS);
        }

        String favouriteArg = extractAndValidateArg(argMultimap, PREFIX_FAVOURITE);

        String countryArg = extractAndValidateArg(argMultimap, PREFIX_COUNTRY);
        if (countryArg != null && !Country.isValidCountry(countryArg)) {
            throw new ParseException(Country.MESSAGE_CONSTRAINTS);
        }

        String regionArg = extractAndValidateArg(argMultimap, PREFIX_REGION);
        if (regionArg != null && !Region.isValidRegion(regionArg)) {
            throw new ParseException(Region.MESSAGE_CONSTRAINTS);
        }

        String noteArg = extractAndValidateArg(argMultimap, PREFIX_NOTE);
        if (noteArg != null && !Note.isValidNote(noteArg)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        boolean isSpecificSearch = nameArg != null || gamertagArg != null || phoneArg != null
                || emailArg != null || groupArg != null || serverArg != null
                || countryArg != null || regionArg != null || favouriteArg != null || noteArg != null;

        if (isSpecificSearch) {
            return new FindCommand(new SpecificAttributesMatchPredicate(
                    nameArg, gamertagArg, phoneArg, emailArg,
                    groupArg, serverArg, favouriteArg, countryArg, regionArg, noteArg));
        } else {
            // Global Search Fallback
            return new FindCommand(new AnyAttributeContainsKeywordsPredicate(trimmedArgs));
        }

    }

    /**
     * Extracts the value for a given prefix.
     * Throws a ParseException if the prefix is present but the value is empty or just whitespace.
     */
    private String extractAndValidateArg(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        if (argMultimap.getValue(prefix).isPresent()) {
            String rawArg = argMultimap.getValue(prefix).get();
            if (prefix == PREFIX_FAVOURITE) {
                String trimmedFavouriteArg = rawArg.trim();
                if (!trimmedFavouriteArg.isEmpty()) {
                    throw new ParseException("The favourite prefix does not take a value. "
                            + "Remove any keyword after the prefix \"" + prefix.getPrefix() + "\".");
                }
                return "Yes";
            }

            String arg = rawArg.trim();
            if (arg.isEmpty()) {
                throw new ParseException(String.format("The search keyword for %s cannot be empty.",
                        prefix.getPrefix()));
            }
            return arg;
        }
        return null;
    }

}
