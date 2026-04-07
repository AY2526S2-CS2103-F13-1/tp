package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private final FavouriteCommandParser favouriteParser = new FavouriteCommandParser(true);
    private final FavouriteCommandParser unfavouriteParser = new FavouriteCommandParser(false);

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        // EP: valid favourite index
        assertParseSuccess(favouriteParser, "1", new FavouriteCommand(INDEX_FIRST_GAMER, true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: non-numeric index
        assertParseFailure(favouriteParser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsUnfavouriteCommand() {
        // EP: valid unfavourite index
        assertParseSuccess(unfavouriteParser, "1", new FavouriteCommand(INDEX_FIRST_GAMER, false));
    }

    @Test
    public void parse_invalidArgs_unfavouriteFailure() {
        // EP: empty argument
        assertParseFailure(unfavouriteParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_outOfRangeIndex_failure() {
        // BV: lower boundary and below-lower boundary
        assertParseFailure(favouriteParser, "0", Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        assertParseFailure(unfavouriteParser, "-1", Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void parse_whitespaceWrappedIndex_success() {
        // BV: valid index with surrounding whitespace
        assertParseSuccess(favouriteParser, "   1   ", new FavouriteCommand(INDEX_FIRST_GAMER, true));
    }

    @Test
    public void parse_multipleInvalidInputs_failure() {
        // Heuristic: combine invalid inputs after single-invalid checks
        assertParseFailure(favouriteParser, "0 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        assertParseFailure(unfavouriteParser, "-1 xyz",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
