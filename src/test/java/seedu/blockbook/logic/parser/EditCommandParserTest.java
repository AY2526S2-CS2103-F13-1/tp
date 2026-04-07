package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.commands.CommandTestUtil.COUNTRY_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.GAMERTAG_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_COUNTRY_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_GAMERTAG_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_REGION_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_SERVER_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.blockbook.logic.commands.CommandTestUtil.REGION_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.SERVER_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GAMERTAG_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_REGION_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_SERVER_BOB;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;
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
        // BV: lower boundary, zero is invalid
        assertParseFailure(parser, "0" + NAME_DESC_BOB, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        // BV: above integer upper bound should be treated as out of range
        assertParseFailure(parser, "999999999999" + NAME_DESC_BOB, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        // EP: preamble contains extra tokens
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        // EP: non-numeric preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValueOneFieldAtATime_failure() {
        // one invalid field at a time
        // EP: invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, seedu.blockbook.model.gamer.Name.MESSAGE_CONSTRAINTS);
        // EP: invalid gamertag
        assertParseFailure(parser, "1" + INVALID_GAMERTAG_DESC,
                seedu.blockbook.model.gamer.GamerTag.MESSAGE_CONSTRAINTS);
        // EP: invalid phone
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC,
                seedu.blockbook.model.gamer.Phone.getPhoneValidationError("911a"));
        // EP: invalid email
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, seedu.blockbook.model.gamer.Email.MESSAGE_CONSTRAINTS);
        // EP: invalid server
        assertParseFailure(parser, "1" + INVALID_SERVER_DESC, seedu.blockbook.model.gamer.Server.MESSAGE_CONSTRAINTS);
        // EP: invalid country
        assertParseFailure(parser, "1" + INVALID_COUNTRY_DESC,
                seedu.blockbook.model.gamer.Country.MESSAGE_CONSTRAINTS);
        // EP: invalid region
        assertParseFailure(parser, "1" + INVALID_REGION_DESC, seedu.blockbook.model.gamer.Region.MESSAGE_CONSTRAINTS);
        // EP: invalid note
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, seedu.blockbook.model.gamer.Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // EP: all valid fields present
        String userInput = INDEX_SECOND_GAMER.getOneBased()
                + GAMERTAG_DESC_BOB
                + NAME_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + SERVER_DESC_BOB
                + COUNTRY_DESC_BOB
                + REGION_DESC_BOB
                + NOTE_DESC_BOB;

        EditCommand.EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder()
                .withGamerTag(VALID_GAMERTAG_BOB)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withServer(VALID_SERVER_BOB)
                .withCountry(VALID_COUNTRY_BOB)
                .withRegion(VALID_REGION_BOB)
                .withNote(VALID_NOTE_BOB)
                .build();

        EditCommand expectedCommand = new EditCommand(INDEX_SECOND_GAMER, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_eachSingleFieldSpecified_success() {
        // Heuristic: each valid input appears at least once in a positive test case
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + NAME_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + GAMERTAG_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withGamerTag(VALID_GAMERTAG_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + PHONE_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withPhone(VALID_PHONE_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + EMAIL_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withEmail(VALID_EMAIL_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + SERVER_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withServer(VALID_SERVER_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + COUNTRY_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withCountry(VALID_COUNTRY_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + REGION_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withRegion(VALID_REGION_BOB).build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + NOTE_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder().withNote(VALID_NOTE_BOB).build()));
    }

    @Test
    public void parse_pairwiseValidFields_success() {
        // Mix strategy: pairwise combinations of valid fields
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder()
                                .withName(VALID_NAME_BOB)
                                .withPhone(VALID_PHONE_BOB)
                                .build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + EMAIL_DESC_BOB + SERVER_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder()
                                .withEmail(VALID_EMAIL_BOB)
                                .withServer(VALID_SERVER_BOB)
                                .build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + COUNTRY_DESC_BOB + REGION_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder()
                                .withCountry(VALID_COUNTRY_BOB)
                                .withRegion(VALID_REGION_BOB)
                                .build()));
        assertParseSuccess(parser, INDEX_FIRST_GAMER.getOneBased() + GAMERTAG_DESC_BOB + NOTE_DESC_BOB,
                new EditCommand(INDEX_FIRST_GAMER,
                        new EditGamerDescriptorBuilder()
                                .withGamerTag(VALID_GAMERTAG_BOB)
                                .withNote(VALID_NOTE_BOB)
                                .build()));
    }

    @Test
    public void parse_repeatedValue_failure() {
        // EP: duplicate prefix in input
        String userInput = INDEX_FIRST_GAMER.getOneBased() + NAME_DESC_BOB + NAME_DESC_BOB;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    @Test
    public void parse_repeatedServerPrefix_failure() {
        // EP: duplicate server prefix in input
        String userInput = INDEX_FIRST_GAMER.getOneBased() + SERVER_DESC_BOB + SERVER_DESC_BOB;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SERVER));
    }

    @Test
    public void parse_multipleInvalidInputs_errorPrecedence() {
        // Heuristic: combined invalid inputs
        // EP: invalid index + invalid field -> index error takes precedence
        assertParseFailure(parser, "-1" + INVALID_NAME_DESC, Messages.MESSAGE_INDEX_OUT_OF_RANGE);

        // EP: duplicate prefix + invalid field -> duplicate-prefix error takes precedence
        String userInput = INDEX_FIRST_GAMER.getOneBased() + SERVER_DESC_BOB + INVALID_SERVER_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SERVER));
    }

    @Test
    public void parse_favouritePrefix_failure() {
        String userInput = INDEX_FIRST_GAMER.getOneBased() + " favourite/true";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
