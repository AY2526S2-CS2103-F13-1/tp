package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.SortCommand;

/**
 * Contains unit tests for {@code SortCommandParser}.
 */
public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArgs_returnsDefaultSortCommand() {
        SortCommand expectedCommand = new SortCommand(new ArrayList<>());
        assertParseSuccess(parser, "", expectedCommand);
        assertParseSuccess(parser, "   ", expectedCommand);
    }

    @Test
    public void parse_singleValidAttribute_success() {
        assertParseSuccess(parser, " name/", new SortCommand(List.of("name")));
        assertParseSuccess(parser, " phone/", new SortCommand(List.of("phone")));
        assertParseSuccess(parser, " email/", new SortCommand(List.of("email")));
        assertParseSuccess(parser, " group/", new SortCommand(List.of("group")));
        assertParseSuccess(parser, " server/", new SortCommand(List.of("server")));
        assertParseSuccess(parser, " favourite/", new SortCommand(List.of("favourite")));
        assertParseSuccess(parser, " country/", new SortCommand(List.of("country")));
        assertParseSuccess(parser, " region/", new SortCommand(List.of("region")));
        assertParseSuccess(parser, " note/", new SortCommand(List.of("note")));
        assertParseSuccess(parser, " gamertag/", new SortCommand(List.of("gamertag")));
    }

    @Test
    public void parse_multipleValidAttributes_success() {
        assertParseSuccess(parser, " name/ phone/",
                new SortCommand(List.of("name", "phone")));
        assertParseSuccess(parser, " phone/ gamertag/",
                new SortCommand(List.of("phone", "gamertag")));
        assertParseSuccess(parser, " name/ phone/ email/",
                new SortCommand(List.of("name", "phone", "email")));
    }

    @Test
    public void parse_caseSensitive_invalidMixedCase_throwsParseException() {
        assertParseFailure(parser, " NAME/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "NAME"));
        assertParseFailure(parser, " Phone/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "Phone"));
        assertParseFailure(parser, " EMAIL/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "EMAIL"));
    }

    @Test
    public void parse_invalidAttribute_throwsParseException() {
        assertParseFailure(parser, " invalid/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "invalid"));
        assertParseFailure(parser, " address/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "address"));
        assertParseFailure(parser, " name/ invalid/",
                String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, "invalid"));
    }

    @Test
    public void parse_duplicateAttribute_throwsParseException() {
        assertParseFailure(parser, " name/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "name"));
        assertParseFailure(parser, " phone/ email/ phone/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "phone"));
        assertParseFailure(parser, " gamertag/ gamertag/ name/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "gamertag, name"));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // No trailing slash
        assertParseFailure(parser, " name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        // Random text mixed in
        assertParseFailure(parser, " name/ abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
