package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;
import static seedu.blockbook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.NameContainsKeywordsPredicate;
import seedu.blockbook.testutil.EditGamerDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_GAMERTAG_AMY = "amy_tag";
    public static final String VALID_GAMERTAG_BOB = "bob_tag";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_GROUP_AMY = "Raid Team";
    public static final String VALID_GROUP_BOB = "DestroySteve";
    public static final String VALID_SERVER_AMY = "127.0.0.1:8080";
    public static final String VALID_SERVER_BOB = "mc.example.com:25565";
    public static final String VALID_COUNTRY_AMY = "Singapore";
    public static final String VALID_COUNTRY_BOB = "Malaysia";
    public static final String VALID_REGION_AMY = "ASIA";
    public static final String VALID_REGION_BOB = "EU";
    public static final String VALID_NOTE_AMY = "I hate steve";
    public static final String VALID_NOTE_BOB = "alt account";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String GAMERTAG_DESC_AMY = " " + PREFIX_GAMERTAG + VALID_GAMERTAG_AMY;
    public static final String GAMERTAG_DESC_BOB = " " + PREFIX_GAMERTAG + VALID_GAMERTAG_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String GROUP_DESC_AMY = " " + PREFIX_GROUP + VALID_GROUP_AMY;
    public static final String GROUP_DESC_BOB = " " + PREFIX_GROUP + VALID_GROUP_BOB;
    public static final String SERVER_DESC_AMY = " " + PREFIX_SERVER + VALID_SERVER_AMY;
    public static final String SERVER_DESC_BOB = " " + PREFIX_SERVER + VALID_SERVER_BOB;
    public static final String COUNTRY_DESC_AMY = " " + PREFIX_COUNTRY + VALID_COUNTRY_AMY;
    public static final String COUNTRY_DESC_BOB = " " + PREFIX_COUNTRY + VALID_COUNTRY_BOB;
    public static final String REGION_DESC_AMY = " " + PREFIX_REGION + VALID_REGION_AMY;
    public static final String REGION_DESC_BOB = " " + PREFIX_REGION + VALID_REGION_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_GAMERTAG_DESC = " " + PREFIX_GAMERTAG + "tag*"; // '*' not allowed in gamertags
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "123";
    public static final String INVALID_SERVER_DESC = " " + PREFIX_SERVER + "server$";
    public static final String INVALID_COUNTRY_DESC = " " + PREFIX_COUNTRY + "SG1";
    public static final String INVALID_REGION_DESC = " " + PREFIX_REGION + "SEA";
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE + "note$";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditGamerDescriptor DESC_AMY;
    public static final EditCommand.EditGamerDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditGamerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withGamerTag(VALID_GAMERTAG_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withServer(VALID_SERVER_AMY)
                .withCountry(VALID_COUNTRY_AMY)
                .withRegion(VALID_REGION_AMY).withNote(VALID_NOTE_AMY)
                .build();
        DESC_BOB = new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withGamerTag(VALID_GAMERTAG_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withServer(VALID_SERVER_BOB)
                .withCountry(VALID_COUNTRY_BOB)
                .withRegion(VALID_REGION_BOB).withNote(VALID_NOTE_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the BlockBook, filtered gamer list and selected gamer in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BlockBook expectedBlockBook = new BlockBook(actualModel.getBlockBook());
        List<Gamer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredGamerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedBlockBook, actualModel.getBlockBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredGamerList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the gamer at the given {@code targetIndex} in the
     * {@code model}'s BlockBook.
     */
    public static void showGamerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGamerList().size());

        Gamer gamer = model.getFilteredGamerList().get(targetIndex.getZeroBased());
        final String[] splitName = gamer.getName().fullName.split("\\s+");
        model.updateFilteredGamerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredGamerList().size());
    }

}
