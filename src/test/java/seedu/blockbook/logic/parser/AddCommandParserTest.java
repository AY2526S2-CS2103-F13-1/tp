package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.commands.CommandTestUtil.COUNTRY_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.GAMERTAG_DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.GAMERTAG_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_COUNTRY_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_GAMERTAG_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_REGION_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.INVALID_SERVER_DESC;
import static seedu.blockbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.blockbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.blockbook.logic.commands.CommandTestUtil.REGION_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.SERVER_DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GAMERTAG_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;
import seedu.blockbook.testutil.GamerBuilder;

public class AddCommandParserTest {

    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_requiredFieldOnly_success() {
        Gamer expectedGamer = new GamerBuilder()
                .withGamerTag(VALID_GAMERTAG_BOB)
                .build();
        Gamer minimalExpectedGamer = new Gamer(
                null,
                new GamerTag(VALID_GAMERTAG_BOB),
                null,
                null,
                new ArrayList<>(),
                null,
                new Favourite(false),
                null,
                null,
                null
        );

        assertParseSuccess(parser, GAMERTAG_DESC_BOB, new AddCommand(minimalExpectedGamer));
    }

    @Test
    public void parse_allOptionalFieldsPresent_success() {
        Gamer expectedGamer = new Gamer(
                new Name(VALID_NAME_BOB),
                new GamerTag(VALID_GAMERTAG_BOB),
                new Phone("22222222"),
                new Email("bob@example.com"),
                java.util.Collections.<Group>emptyList(),
                new Server("mc.example.com:25565"),
                expectedFavouriteFalse(),
                new Country("Malaysia"),
                new Region("EU"),
                new Note("alt account")
        );

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + GAMERTAG_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + SERVER_DESC_BOB + COUNTRY_DESC_BOB + REGION_DESC_BOB + NOTE_DESC_BOB,
                new AddCommand(expectedGamer));
    }

    @Test
    public void parse_nameAndCountryNormalization_success() {
        String userInput = PREAMBLE_WHITESPACE
                + " " + PREFIX_GAMERTAG + "gamer_tag"
                + " " + PREFIX_NAME + "aLiCe   pauLINE"
                + " " + PREFIX_COUNTRY + "united   states"
                + " " + PREFIX_REGION + "asia";

        Gamer expectedGamer = new Gamer(
                new Name("Alice Pauline"),
                new GamerTag("gamer_tag"),
                null,
                null,
                new ArrayList<>(),
                null,
                expectedFavouriteFalse(),
                new Country("United States"),
                new Region("ASIA"),
                null
        );

        assertParseSuccess(parser, userInput, new AddCommand(expectedGamer));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, NAME_DESC_BOB, expectedMessage);
        assertParseFailure(parser, VALID_NAME_BOB + VALID_GAMERTAG_BOB, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GAMERTAG_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedGamerString = GAMERTAG_DESC_BOB + NAME_DESC_BOB;

        assertParseFailure(parser, GAMERTAG_DESC_AMY + validExpectedGamerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GAMERTAG));

        assertParseFailure(parser, NAME_DESC_AMY + validExpectedGamerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser,
                validExpectedGamerString + GAMERTAG_DESC_AMY + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GAMERTAG, PREFIX_NAME));
    }

    @Test
    public void parse_repeatedOptionalPrefixes_failure() {
        String base = GAMERTAG_DESC_BOB;

        assertParseFailure(parser, base + EMAIL_DESC_BOB + EMAIL_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, base + PHONE_DESC_BOB + PHONE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, base + SERVER_DESC_BOB + SERVER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SERVER));

        assertParseFailure(parser, base + COUNTRY_DESC_BOB + COUNTRY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COUNTRY));

        assertParseFailure(parser, base + REGION_DESC_BOB + REGION_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REGION));

        assertParseFailure(parser, base + NOTE_DESC_BOB + NOTE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_GAMERTAG_DESC, GamerTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_PHONE_DESC,
                Phone.getPhoneValidationError("911a"));
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_SERVER_DESC, Server.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_COUNTRY_DESC, Country.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_REGION_DESC, Region.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidValues_reportsFirstInvalidValue() {
        assertParseFailure(parser,
                GAMERTAG_DESC_BOB + INVALID_NAME_DESC + INVALID_EMAIL_DESC,
                Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                INVALID_GAMERTAG_DESC + INVALID_NAME_DESC,
                GamerTag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyGamertagValue_failure() {
        assertParseFailure(parser, " " + PREFIX_GAMERTAG, GamerTag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyOptionalValue_failure() {
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_PHONE, "Phone number cannot be empty");
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_EMAIL, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_SERVER, Server.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_COUNTRY, Country.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_REGION, Region.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, GAMERTAG_DESC_BOB + " " + PREFIX_NOTE, Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixTakesPriorityOverInvalidValue_failure() {
        assertParseFailure(parser,
                GAMERTAG_DESC_BOB + EMAIL_DESC_BOB + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
    }

    private seedu.blockbook.model.gamer.Favourite expectedFavouriteFalse() {
        return new seedu.blockbook.model.gamer.Favourite(false);
    }
}
