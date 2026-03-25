package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_SECOND_GAMER;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_THIRD_GAMER;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertParseSuccess(parser, "1", deleteCommand);
    }

    @Test
    public void parse_validMultipleArgs_returnsDeleteCommand() {
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(INDEX_SECOND_GAMER);
        indexList.add(INDEX_THIRD_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertParseSuccess(parser, "1 2 3", deleteCommand);
    }

    @Test
    public void parse_validMultipleArgsWithExtraWhitespace_returnsDeleteCommand() {
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(INDEX_SECOND_GAMER);
        indexList.add(INDEX_THIRD_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertParseSuccess(parser, "   1   2   3   ", deleteCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTokenAmongIndexes_throwsParseException() {
        assertParseFailure(parser, "1 a 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}


