package seedu.blockbook.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;

import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.EditCommand;
import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GAMERTAG,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_SERVER,
                PREFIX_COUNTRY,
                PREFIX_REGION,
                PREFIX_NOTE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            String trimmedPreamble = argMultimap.getPreamble().trim();
            if (trimmedPreamble.matches("-?\\d+")) {
                logger.finer("Edit parse failed: index out of range (" + trimmedPreamble + ").");
                throw new ParseException(Messages.MESSAGE_INDEX_OUT_OF_RANGE, pe);
            }
            logger.finer("Edit parse failed: invalid preamble (" + trimmedPreamble + ").");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_GAMERTAG,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_SERVER,
                PREFIX_COUNTRY,
                PREFIX_REGION,
                PREFIX_NOTE
        );

        EditGamerDescriptor descriptor = new EditGamerDescriptor();

        if (argMultimap.getValue(PREFIX_GAMERTAG).isPresent()) {
            descriptor.setGamerTag(ParserUtil.parseGamerTag(argMultimap.getValue(PREFIX_GAMERTAG).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_SERVER).isPresent()) {
            descriptor.setServer(ParserUtil.parseServer(argMultimap.getValue(PREFIX_SERVER).get()));
        }
        if (argMultimap.getValue(PREFIX_COUNTRY).isPresent()) {
            descriptor.setCountry(ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get()));
        }
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            descriptor.setRegion(ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            descriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            logger.finer("Edit parse failed: no fields to edit.");
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        logger.fine("Parsed edit command for index " + index.getOneBased() + ".");
        return new EditCommand(index, descriptor);
    }
}
