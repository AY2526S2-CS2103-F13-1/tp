package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.TypicalIndexes;

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
     * Verifies that the command finds the exact gamer contact as supplied by index.
     */
    @Test
    public void execute_gamerFound_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_GAMER;
        Gamer targetGamer = model.getFilteredGamerList().get(targetIndex.getZeroBased());
        ViewCommand command = new ViewCommand(targetIndex);

        // Construct the exact expected string output based on the ViewCommand logic
        String expectedMessage = "Name: " + Messages.formatNullable(targetGamer.getName())
                + " Gamertag: " + targetGamer.getGamerTag()
                + " Phone: " + Messages.formatNullable(targetGamer.getPhone())
                + " Email: " + Messages.formatNullable(targetGamer.getEmail())
                + " Group: " + Messages.formatNullable(targetGamer.getGroup())
                + " Server: " + Messages.formatNullable(targetGamer.getServer())
                + " Favourite: " + Messages.formatNullable(targetGamer.getFavourite())
                + " Country: " + Messages.formatNullable(targetGamer.getCountry())
                + " Region: " + Messages.formatNullable(targetGamer.getRegion())
                + " Note: " + Messages.formatNullable(targetGamer.getNote());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that view uses the currently displayed list when it is filtered.
     */
    @Test
    public void execute_filteredList_viewIndexSuccess() {
        showGamerAtIndex(model, TypicalIndexes.INDEX_FIRST_GAMER);
        showGamerAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST_GAMER);

        Index targetIndex = TypicalIndexes.INDEX_FIRST_GAMER;
        Gamer targetGamer = model.getFilteredGamerList().get(targetIndex.getZeroBased());
        ViewCommand command = new ViewCommand(targetIndex);

        expectedModel.updateFilteredGamerList(predicate);

        String expectedMessage = Messages.format(targetGamer);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that an out-of-range index returns the index-out-of-range message.
     */
    @Test
    public void execute_gamerNotFound_showsNotFoundMessage() {
        Index outOfRangeIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        ViewCommand command = new ViewCommand(outOfRangeIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    /**
     * Verifies the that the equals() method works as intended in comparison.
     */
    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        ViewCommand viewFirstCommand = new ViewCommand(firstIndex);
        ViewCommand viewSecondCommand = new ViewCommand(secondIndex);

        // same object returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstIndex);
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
        Index index = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(index);

        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
