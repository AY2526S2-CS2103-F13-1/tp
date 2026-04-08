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

        // EP (valid): empty/whitespace-only input belongs to the default-sort partition.
        assertParseSuccess(parser, "", expectedCommand);
        assertParseSuccess(parser, "   ", expectedCommand);
    }

    @Test
    public void parse_singleValidAttribute_success() {
        // EP (valid) + input-combination heuristic:
        // each valid canonical attribute appears at least once in a positive test case.
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
    public void parse_singleValidAliasAttribute_success() {
        // EP (valid) + input-combination heuristic:
        // each valid alias appears at least once in a positive test case.
        assertParseSuccess(parser, " g/", new SortCommand(List.of("gamertag")));
        assertParseSuccess(parser, " n/", new SortCommand(List.of("name")));
        assertParseSuccess(parser, " p/", new SortCommand(List.of("phone")));
        assertParseSuccess(parser, " e/", new SortCommand(List.of("email")));
        assertParseSuccess(parser, " gr/", new SortCommand(List.of("group")));
        assertParseSuccess(parser, " s/", new SortCommand(List.of("server")));
        assertParseSuccess(parser, " fav/", new SortCommand(List.of("favourite")));
        assertParseSuccess(parser, " c/", new SortCommand(List.of("country")));
        assertParseSuccess(parser, " r/", new SortCommand(List.of("region")));
    }

    @Test
    public void parse_multipleValidAttributes_success() {
        // Input combination (all-valid): mixed canonical/alias attributes should preserve priority order.
        assertParseSuccess(parser, " name/ phone/",
                new SortCommand(List.of("name", "phone")));
        assertParseSuccess(parser, " phone/ gamertag/",
                new SortCommand(List.of("phone", "gamertag")));
        assertParseSuccess(parser, " name/ phone/ email/",
                new SortCommand(List.of("name", "phone", "email")));
        assertParseSuccess(parser, " n/ phone/ g/",
                new SortCommand(List.of("name", "phone", "gamertag")));
        assertParseSuccess(parser, "   n/   phone/   g/   ",
                new SortCommand(List.of("name", "phone", "gamertag")));
    }

    @Test
    public void parse_multipleValidAliasAttributes_success() {
        // Input combination (all-valid): alias-only combinations should preserve order.
        assertParseSuccess(parser, " n/ p/",
                new SortCommand(List.of("name", "phone")));
        assertParseSuccess(parser, " g/ e/ s/",
                new SortCommand(List.of("gamertag", "email", "server")));
    }

    @Test
    public void parse_caseSensitiveMixedCase_throwsParseException() {
        // EP (invalid token): case-mismatched tokens should be rejected.
        assertParseFailure(parser, " NAME/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "NAME"));
        assertParseFailure(parser, " Phone/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "Phone"));
        assertParseFailure(parser, " EMAIL/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "EMAIL"));
        assertParseFailure(parser, " sErVeR/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "sErVeR"));
    }

    @Test
    public void parse_invalidAttribute_throwsParseException() {
        // EP (invalid token): single-invalid cases, aligned with
        // the "test invalid inputs individually before combining" heuristic.
        assertParseFailure(parser, " x/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "x"));
        assertParseFailure(parser, " invalid/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "invalid"));
        assertParseFailure(parser, " address/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "address"));
        assertParseFailure(parser, " name/ invalid/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "invalid"));
        assertParseFailure(parser, " no/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "no"));
    }

    @Test
    public void parse_multipleInvalidAttributes_throwsParseException() {
        // Input-combination follow-up: after single-invalid coverage,
        // add a multi-invalid case to verify aggregate invalid-attribute reporting.
        assertParseFailure(parser, " invalid/ address/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTES, "invalid, address"));
        assertParseFailure(parser, " name/ invalid/ address/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTES, "invalid, address"));
        assertParseFailure(parser, " NAME/ EMAIL/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTES, "NAME, EMAIL"));
    }

    @Test
    public void parse_duplicateAttribute_throwsParseException() {
        // EP (invalid duplicate): canonical/canonical and canonical/alias collisions.
        assertParseFailure(parser, " name/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "name"));
        assertParseFailure(parser, " phone/ email/ phone/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "phone"));
        assertParseFailure(parser, " gamertag/ gamertag/ name/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "gamertag, name"));
        assertParseFailure(parser, " gamertag/ g/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "gamertag"));
    }

    @Test
    public void parse_duplicateAliasThenCanonical_throwsParseException() {
        // EP (invalid duplicate): detection should be independent of alias/canonical order.
        assertParseFailure(parser, " g/ gamertag/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "gamertag"));
        assertParseFailure(parser, " n/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "name"));
    }

    @Test
    public void parse_allValidAttributes_success() {
        // Boundary-focused positive case: upper valid limit of unique sortable attributes (10).
        assertParseSuccess(parser,
                " name/ phone/ email/ group/ server/ favourite/ country/ region/ note/ gamertag/",
                new SortCommand(List.of(
                        "name", "phone", "email", "group", "server",
                        "favourite", "country", "region", "note", "gamertag")));
    }

    @Test
    public void parse_allValidAttributesPlusOneDuplicate_throwsParseException() {
        // Boundary-adjacent robustness case: one extra token beyond the valid full set
        // (via alias duplicate) should fail as a duplicate.
        assertParseFailure(parser,
                " name/ phone/ email/ group/ server/ favourite/ country/ region/ note/ gamertag/ n/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "name"));
    }

    @Test
    public void parse_duplicateFirstAndLastAttribute_throwsParseException() {
        // Robustness: duplicate detection for non-adjacent repeated attributes.
        assertParseFailure(parser, " name/ phone/ email/ group/ name/",
                String.format(SortCommandParser.MESSAGE_DUPLICATE_ATTRIBUTE, "name"));
    }

    @Test
    public void parse_invalidBeforeValidAttribute_throwsParseException() {
        // Input-order robustness: invalid-token outcome should not depend on token position.
        assertParseFailure(parser, " invalid/ name/",
                String.format(SortCommandParser.MESSAGE_INVALID_ATTRIBUTE, "invalid"));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // EP (invalid format): malformed token (missing trailing slash).
        assertParseFailure(parser, " name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        // EP (invalid format): slash-only token with no attribute name.
        assertParseFailure(parser, " /",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        // EP (invalid format): valid token followed by trailing junk text.
        assertParseFailure(parser, " name/ abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        // EP (invalid format): malformed delimiter/punctuation.
        assertParseFailure(parser, " name//",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " gamer-tag/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
