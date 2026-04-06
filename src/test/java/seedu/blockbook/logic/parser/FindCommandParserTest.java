package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.blockbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.FindCommand;
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;
import seedu.blockbook.model.gamer.SpecificAttributesMatchPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    /**
     * Verifies that an empty argument throws MESSAGE_INVALID_COMMAND_FORMAT
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that an empty prefix argument for all attribute types return the expected message
     * Favourite is excluded as it does not need any params behind the prefix.
     */
    @Test
    public void parse_emptyPrefixArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME + " ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_NAME.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_GAMERTAG + " ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_GAMERTAG.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_PHONE + "  \t ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_PHONE.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_EMAIL + " \n ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_EMAIL.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_GROUP + " \t ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_GROUP.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_SERVER + " ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_SERVER.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_COUNTRY + "  ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_COUNTRY.getPrefix()));

        assertParseFailure(parser, " " + PREFIX_REGION + " \t \n ",
                String.format("The search keyword for %s cannot be empty.", PREFIX_REGION.getPrefix()));

    }

    /**
     * Verifies that invalid name argument throws the expected exception with NAME MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidName_throwsParseException() {
        // Contains illegal symbols
        assertParseFailure(parser, " " + PREFIX_NAME + "Kester!!!", Name.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid gamertag argument throws the expected exception with GAMERTAG MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidGamertag_throwsParseException() {
        // Assuming your constraints forbid special symbols like '*' or '!'
        assertParseFailure(parser, " " + PREFIX_GAMERTAG + "Minecraft_Pro*", GamerTag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid phone argument throws the expected exception with PHONE MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidPhone_throwsParseException() {
        // Contains letters instead of just numbers
        assertParseFailure(parser, " " + PREFIX_PHONE + "9123abcd", Phone.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid email argument throws the expected exception with EMAIL MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidLaxEmail_throwsParseException() {
        // Contains spaces
        assertParseFailure(parser, " " + PREFIX_EMAIL + "john doe@example.com", Email.MESSAGE_LAX_CONSTRAINTS);
    }

    /**
     * Verifies that invalid group argument throws the expected exception with GROUP MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidGroup_throwsParseException() {
        // Assuming groups must be alphanumeric without spaces
        assertParseFailure(parser, " " + PREFIX_GROUP + "Elite Team*", Group.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid server argument throws the expected exception with SERVER MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidServer_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SERVER + "Asia^Pacific", Server.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid country argument throws the expected exception with COUNTRY MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidCountry_throwsParseException() {
        // Country with numbers
        assertParseFailure(parser, " " + PREFIX_COUNTRY + "S1ngapore", Country.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that invalid region argument throws the expected exception with REGION MESSAGE_CONSTRAINTS.
     */
    @Test
    public void parse_invalidRegion_throwsParseException() {
        // Region with illegal symbols
        assertParseFailure(parser, " " + PREFIX_REGION + "SEA_#1", Region.MESSAGE_CONSTRAINTS);
    }

    /**
     * Verifies that valid global search format returns FindCommand with AnyAttributeContainsKeywordsPredicate.
     */
    @Test
    public void parse_validGlobalSearch_returnsFindCommand() {
        // Format 1: No prefixes used. Should parse as AnyAttributeContainsKeywordsPredicate
        FindCommand expectedFindCommand =
                new FindCommand(new AnyAttributeContainsKeywordsPredicate("Alice Bob"));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // whitespaces at the ends should be trimmed properly by the parser
        assertParseSuccess(parser, " \n Alice Bob \t", expectedFindCommand);
    }

    /**
     * Verifies that mixing global and specific search formats is rejected.
     */
    @Test
    public void parse_mixedGlobalAndSpecific_throwsParseException() {
        assertParseFailure(parser, "herobrine " + PREFIX_NAME + "Steve",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that valid specific search format returns FindCommand with SpecificAttributesMatchPredicate.
     */
    @Test
    public void parse_validSpecificSearch_returnsFindCommand() {
        // Format 2: Prefixes used. Should parse as SpecificAttributesMatchPredicate

        // 1. Single attribute search (Partial phone search)
        // Constructor params: name, gamertag, phone, email, group, server, favourite, country, region
        FindCommand expectedSingleCommand = new FindCommand(new SpecificAttributesMatchPredicate(
                null, null, "987", null, null, null, null, null, null, null));
        assertParseSuccess(parser, " " + PREFIX_PHONE + "987", expectedSingleCommand);

        // 2. Multiple attribute search (Name and lax email)
        FindCommand expectedMultiCommand = new FindCommand(new SpecificAttributesMatchPredicate(
                "Alice", null, null, "gmail", null, null, null, null, null, null));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_EMAIL + "gmail", expectedMultiCommand);
    }

}

