package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_SECOND_GAMER;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Gamer gamerToDelete = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GAMER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                Messages.format(gamerToDelete));

        ModelManager expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(gamerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        Gamer gamerToDelete = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GAMER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                Messages.format(gamerToDelete));

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(gamerToDelete);
        showNoGamer(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        Index outOfBoundIndex = INDEX_SECOND_GAMER;
        // ensures that outOfBoundIndex is still in bounds of BlockBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBlockBook().getGamerList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_GAMER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_GAMER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_GAMER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different gamer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoGamer(Model model) {
        model.updateFilteredGamerList(p -> false);

        assertTrue(model.getFilteredGamerList().isEmpty());
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();

        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(1));

        assertCommandFailure(deleteCommand, emptyModel,
                Messages.MESSAGE_EMPTY_CONTACT_LIST);
    }

    @Test
    public void execute_deleteOnlyGamerInFilteredList_success() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        Gamer gamerToDelete = model.getFilteredGamerList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GAMER);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(gamerToDelete);
        showNoGamer(expectedModel);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                Messages.format(gamerToDelete));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleDeletes_success() {
        DeleteCommand deleteFirst = new DeleteCommand(INDEX_FIRST_GAMER);
        assertCommandSuccess(deleteFirst, model,
                String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                        Messages.format(model.getFilteredGamerList().get(0))),
                model);

        DeleteCommand deleteSecond = new DeleteCommand(INDEX_FIRST_GAMER);
        Gamer nextGamer = model.getFilteredGamerList().get(0);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(nextGamer);

        assertCommandSuccess(deleteSecond, model,
                String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                        Messages.format(nextGamer)),
                expectedModel);
    }
}



