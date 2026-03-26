package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamertagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    /**
     * Sets up typical models for list command tests.
     */
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
    }


    /**
     * Verifies that the command finds the exact gamer contact as supplied by Gamertag.
     */
    @Test
    public void execute_gamerFound_success() {
        // Extract the first gamer from the typical test data to ensure a match
        Gamer targetGamer = model.getFilteredGamerList().get(0);
        String keyword = targetGamer.getGamerTag().toString();

        GamertagContainsKeywordPredicate predicate = new GamertagContainsKeywordPredicate(keyword);
        ViewCommand command = new ViewCommand(predicate);

        // Set up the expected model's filtered list
        expectedModel.updateFilteredGamerList(predicate);

        // Construct the exact expected string output based on the ViewCommand logic
        String expectedMessage = "Name: " + targetGamer.getName()
                + " Gamertag: " + targetGamer.getGamerTag()
                + " Phone: " + targetGamer.getPhone()
                + " Email: " + targetGamer.getEmail()
                + " Group: " + targetGamer.getGroups()
                + " Server: " + targetGamer.getServer()
                + " Favourite: " + targetGamer.getFavourite()
                + " Country: " + targetGamer.getCountry()
                + " Region: " + targetGamer.getRegion()
                + " Note: " + targetGamer.getNote();

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that the command finds the exact gamer contact as supplied by Gamertag regardless of character case.
     */
    @Test
    public void execute_caseInsensitiveSearch_success() {
        Gamer targetGamer = model.getFilteredGamerList().get(0);
        String originalGamertag = targetGamer.getGamerTag().toString();

        // Invert the case of the gamertag to test case-insensitivity
        String invertedCaseKeyword = originalGamertag.toLowerCase().equals(originalGamertag)
                ? originalGamertag.toUpperCase()
                : originalGamertag.toLowerCase();

        GamertagContainsKeywordPredicate predicate = new GamertagContainsKeywordPredicate(invertedCaseKeyword);
        ViewCommand command = new ViewCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);

        String expectedMessage = "Name: " + targetGamer.getName()
                + " Gamertag: " + targetGamer.getGamerTag()
                + " Phone: " + targetGamer.getPhone()
                + " Email: " + targetGamer.getEmail()
                + " Group: " + targetGamer.getGroups()
                + " Server: " + targetGamer.getServer()
                + " Favourite: " + targetGamer.getFavourite()
                + " Country: " + targetGamer.getCountry()
                + " Region: " + targetGamer.getRegion()
                + " Note: " + targetGamer.getNote();

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that the command returns the MESSAGE_GAMERTAG_NOT_FOUND when the command
     * cannot locate a gamer contact with the specified Gamertag.
     */
    @Test
    public void execute_gamerNotFound_showsNotFoundMessage() {
        String nonexistentKeyword = "ThisGamertagShouldNotExist123";
        GamertagContainsKeywordPredicate predicate = new GamertagContainsKeywordPredicate(nonexistentKeyword);
        ViewCommand command = new ViewCommand(predicate);

        // The expected model should have an empty filtered list
        expectedModel.updateFilteredGamerList(predicate);

        assertCommandSuccess(command, model, Messages.MESSAGE_GAMERTAG_NOT_FOUND, expectedModel);
    }

    /**
     * Verifies the that the equals() method works as intended in comparison.
     */
    @Test
    public void equals() {
        GamertagContainsKeywordPredicate firstPredicate =
                new GamertagContainsKeywordPredicate("first");
        GamertagContainsKeywordPredicate secondPredicate =
                new GamertagContainsKeywordPredicate("second");

        ViewCommand viewFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondPredicate);

        // same object returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types returns false
        assertFalse(viewFirstCommand.equals(1));

        // null returns false
        assertFalse(viewFirstCommand.equals(null));

        // different gamer returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public void toStringMethod() {
        GamertagContainsKeywordPredicate predicate = new GamertagContainsKeywordPredicate("keyword");
        ViewCommand viewCommand = new ViewCommand(predicate);

        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
