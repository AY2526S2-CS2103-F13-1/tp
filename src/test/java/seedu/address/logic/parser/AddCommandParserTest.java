package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GAMERTAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GAMERTAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GAMERTAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GAMERTAG_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.gamer.Gamer;
import seedu.address.model.gamer.GamerTag;
import seedu.address.model.gamer.Name;
import seedu.address.testutil.GamerBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Gamer expectedGamer = new GamerBuilder().withName(VALID_NAME_BOB).withGamerTag(VALID_GAMERTAG_BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + GAMERTAG_DESC_BOB,
                new AddCommand(expectedGamer));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedGamerString = NAME_DESC_BOB + GAMERTAG_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedGamerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple gamer tags
        assertParseFailure(parser, GAMERTAG_DESC_AMY + validExpectedGamerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GAMERTAG));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedGamerString + NAME_DESC_AMY + GAMERTAG_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_GAMERTAG));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + GAMERTAG_DESC_BOB, expectedMessage);

        // missing gamer tag prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_GAMERTAG_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_GAMERTAG_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GAMERTAG_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid gamer tag
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_GAMERTAG_DESC, GamerTag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_GAMERTAG_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + GAMERTAG_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
