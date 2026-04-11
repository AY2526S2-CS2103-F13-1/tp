package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE;
import static seedu.blockbook.logic.Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.commands.GroupAddCommand;

public class GroupAddCommandParserTest {
    private final GroupAddCommandParser parser = new GroupAddCommandParser();

    @Test
    public void parse_validArgs_returnsGroupAddCommand() {
        GroupAddCommand expectedCommand = new GroupAddCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        assertParseSuccess(parser, " 1 2 ", expectedCommand);
        assertParseSuccess(parser, "\n 1 \t 2 \n", expectedCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1 b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " +1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexes_throwsParseException() {
        assertParseFailure(parser, " 0 1", MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " -1 1", MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 1 0", MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 1 -1", MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 0 0", MESSAGE_GAMER_INDEX_OUT_OF_RANGE + "\n"
                + MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " -1 -1", MESSAGE_GAMER_INDEX_OUT_OF_RANGE + "\n"
                + MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void parse_overflowIndexes_throwsParseException() {
        assertParseFailure(parser, " 9999999999999999999 1", MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
        assertParseFailure(parser, " 1 9999999999999999999", MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
    }
}
