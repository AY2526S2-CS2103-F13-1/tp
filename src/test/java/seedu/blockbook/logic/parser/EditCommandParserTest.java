package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.commands.CommandTestUtil.COUNTRY_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.GAMERTAG_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.blockbook.logic.commands.CommandTestUtil.REGION_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.SERVER_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GAMERTAG_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_REGION_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_SERVER_BOB;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_SECOND_GAMER;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.EditCommand;
import seedu.blockbook.testutil.EditGamerDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // EP: missing index
        assertParseFailure(parser, VALID_NAME_BOB, MESSAGE_INVALID_FORMAT);
        // EP: missing fields
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
        // EP: empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // EP: invalid numeric index (negative)
        assertParseFailure(parser, "-5" + NAME_DESC_BOB, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        // EP: invalid numeric index (zero)
        assertParseFailure(parser, "0" + NAME_DESC_BOB, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        // EP: preamble contains extra tokens
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        // EP: non-numeric preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // EP: invalid field value
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, seedu.blockbook.model.gamer.Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // EP: all valid fields present
        String userInput = INDEX_SECOND_GAMER.getOneBased()
                + GAMERTAG_DESC_BOB
                + NAME_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + GROUP_DESC_BOB
                + SERVER_DESC_BOB
                // + FAVOURITE_DESC_BOB
                + COUNTRY_DESC_BOB
                + REGION_DESC_BOB
                + NOTE_DESC_BOB;

        EditCommand.EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder()
                .withGamerTag(VALID_GAMERTAG_BOB)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withGroup(VALID_GROUP_BOB)
                .withServer(VALID_SERVER_BOB)
                // .withFavourite(VALID_FAVOURITE_BOB)
                .withCountry(VALID_COUNTRY_BOB)
                .withRegion(VALID_REGION_BOB)
                .withNote(VALID_NOTE_BOB)
                .build();

        EditCommand expectedCommand = new EditCommand(INDEX_SECOND_GAMER, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedValue_failure() {
        // EP: duplicate prefix in input
        String userInput = INDEX_FIRST_GAMER.getOneBased() + NAME_DESC_BOB + NAME_DESC_BOB;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
