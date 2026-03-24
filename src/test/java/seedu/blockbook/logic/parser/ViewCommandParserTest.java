package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.blockbook.model.gamer.GamerTag.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.ViewCommand;
import seedu.blockbook.model.gamer.GamertagContainsKeywordPredicate;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    /**
     * Verifies that the expected command is returned.
     */
    @Test
    public void parse_validArgs_returnsViewCommand() {
        ViewCommand expectedCommand =
                new ViewCommand(new GamertagContainsKeywordPredicate("Steve"));

        assertParseSuccess(parser, " " + PREFIX_GAMERTAG + "Steve", expectedCommand);

        // valid input with leading/trailing whitespaces
        assertParseSuccess(parser, " \n " + PREFIX_GAMERTAG + "Steve \t", expectedCommand);
    }

    /**
     * Verifies that the MESSAGE_INVALID_COMMAND_FORMAT is returned, together with the MESSAGE_USAGE for ViewCommand.
     * Given method call.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        // purely empty arguments
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that the MESSAGE_INVALID_COMMAND_FORMAT is returned, together with the MESSAGE_USAGE for ViewCommand.
     * Given missing prefix.
     */
    @Test
    public void parse_missingPrefix_throwsParseException() {
        // missing the 'gamertag/' prefix entirely
        assertParseFailure(parser, " Steve",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that the MESSAGE_CONSTRAINTS is returned, given an invalid Gamertag
     */
    @Test
    public void parse_invalidGamertagValue_throwsParseException() {
        // prefix is present, but the gamertag value is missing
        assertParseFailure(parser, " " + PREFIX_GAMERTAG + "///", MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that the MESSAGE_DUPLICATE_FIELDS is returned, given duplicate prefixes.
     */
    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // multiple gamertags provided
        String userInput = " " + PREFIX_GAMERTAG + "Steve " + PREFIX_GAMERTAG + "Steve2";
        assertParseFailure(parser, userInput, MESSAGE_DUPLICATE_FIELDS + PREFIX_GAMERTAG);
    }
}
