package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    /**
     * Verifies that the expected command is returned.
     */
    @Test
    public void parse_validArgs_returnsViewCommand() {
        ViewCommand expectedCommand = new ViewCommand(Index.fromOneBased(1));

        // valid input with leading/trailing whitespaces
        assertParseSuccess(parser, " \n 1 \t", expectedCommand);
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
     * Verifies that invalid arguments return MESSAGE_INVALID_COMMAND_FORMAT.
     */
    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}
