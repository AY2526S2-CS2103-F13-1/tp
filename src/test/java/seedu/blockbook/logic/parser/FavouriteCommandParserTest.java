package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private final FavouriteCommandParser favouriteParser = new FavouriteCommandParser(true);
    private final FavouriteCommandParser unfavouriteParser = new FavouriteCommandParser(false);

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(favouriteParser, "1", new FavouriteCommand(INDEX_FIRST_GAMER, true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(favouriteParser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsUnFavouriteCommand() {
        assertParseSuccess(unfavouriteParser, "1", new FavouriteCommand(INDEX_FIRST_GAMER, false));
    }

    @Test
    public void parse_invalidArgs_unfavoriteFailure() {
        assertParseFailure(unfavouriteParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
