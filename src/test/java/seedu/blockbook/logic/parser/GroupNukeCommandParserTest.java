package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INDEX_OUT_OF_RANGE;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.commands.GroupNukeCommand;

public class GroupNukeCommandParserTest {
    private final GroupNukeCommandParser parser = new GroupNukeCommandParser();

    @Test
    public void parse_validArgs_returnsGroupNukeCommand() {
        GroupNukeCommand expectedCommand = new GroupNukeCommand(Index.fromOneBased(1), "");

        // valid input with leading/trailing whitespaces
        assertParseSuccess(parser, " \n 1 \t", expectedCommand);
    }

    @Test
    public void parse_validArgsWithConfirmationCode_returnsGroupNukeCommand() {
        GroupNukeCommand expectedCommand = new GroupNukeCommand(Index.fromOneBased(1), " CONFIRM123");
        assertParseSuccess(parser, " 1 CONFIRM123 ", expectedCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupNukeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupNukeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1s",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupNukeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " +2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupNukeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 9999999999999999999", MESSAGE_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " -1", MESSAGE_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 0", MESSAGE_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupNukeCommand.MESSAGE_USAGE));
    }
}
