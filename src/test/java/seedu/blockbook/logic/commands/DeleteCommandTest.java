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
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_THIRD_GAMER;

import java.util.ArrayList;

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
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                gamerToDelete.getGamerTag());

        ModelManager expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(gamerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        Gamer gamerToDelete = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                gamerToDelete.getGamerTag());

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

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void equals() {
        ArrayList<Index> indexListFirstGamer = new ArrayList<>();
        indexListFirstGamer.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteFirstCommand = new DeleteCommand(indexListFirstGamer);

        ArrayList<Index> indexListSecondGamer = new ArrayList<>();
        indexListSecondGamer.add(INDEX_SECOND_GAMER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(indexListSecondGamer);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ArrayList<Index> indexListFirstGamerCopy = new ArrayList<>();
        indexListFirstGamerCopy.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(indexListFirstGamerCopy);
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

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(targetIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndexes=" + indexList + "}";
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

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(Index.fromOneBased(1));
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertCommandFailure(deleteCommand, emptyModel,
                Messages.MESSAGE_EMPTY_CONTACT_LIST);
    }

    @Test
    public void execute_deleteOnlyGamerInFilteredList_success() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        Gamer gamerToDelete = model.getFilteredGamerList().get(0);
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(gamerToDelete);
        showNoGamer(expectedModel);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                gamerToDelete.getGamerTag());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleDeletes_success() {
        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteFirst = new DeleteCommand(indexList);

        Gamer gamerToDelete = model.getFilteredGamerList().get(0);

        Model expectedModelAfterDeleting = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModelAfterDeleting.deleteGamer(gamerToDelete);

        assertCommandSuccess(deleteFirst, model,
                String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                        gamerToDelete.getGamerTag()),
                expectedModelAfterDeleting);

        ArrayList<Index> indexListCopy = new ArrayList<>();
        indexListCopy.add(INDEX_FIRST_GAMER);
        DeleteCommand deleteSecond = new DeleteCommand(indexListCopy);
        Gamer nextGamer = model.getFilteredGamerList().get(0);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(nextGamer);

        assertCommandSuccess(deleteSecond, model,
                String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                        nextGamer.getGamerTag()),
                expectedModel);
    }

    @Test
    public void execute_multipleIndexesSingleCommandUnfilteredList_success() {
        Gamer firstGamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer thirdGamer = model.getFilteredGamerList().get(INDEX_THIRD_GAMER.getZeroBased());

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(INDEX_THIRD_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(thirdGamer);
        expectedModel.deleteGamer(firstGamer);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                firstGamer.getGamerTag() + ", " + thirdGamer.getGamerTag());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unsortedMultipleIndexesSingleCommand_success() {
        Gamer firstGamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer secondGamer = model.getFilteredGamerList().get(INDEX_SECOND_GAMER.getZeroBased());
        Gamer thirdGamer = model.getFilteredGamerList().get(INDEX_THIRD_GAMER.getZeroBased());

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_THIRD_GAMER);
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(INDEX_SECOND_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(thirdGamer);
        expectedModel.deleteGamer(secondGamer);
        expectedModel.deleteGamer(firstGamer);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                firstGamer.getGamerTag() + ", " + secondGamer.getGamerTag() + ", " + thirdGamer.getGamerTag());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIndexesSingleCommand_deletesOnlyOncePerIndex() {
        Gamer firstGamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer secondGamer = model.getFilteredGamerList().get(INDEX_SECOND_GAMER.getZeroBased());

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(INDEX_SECOND_GAMER);
        indexList.add(INDEX_SECOND_GAMER);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.deleteGamer(secondGamer);
        expectedModel.deleteGamer(firstGamer);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GAMER_SUCCESS,
                firstGamer.getGamerTag() + ", " + secondGamer.getGamerTag());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleIndexesOneInvalid_throwsCommandExceptionAndNoDeletion() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);

        ArrayList<Index> indexList = new ArrayList<>();
        indexList.add(INDEX_FIRST_GAMER);
        indexList.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexList);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }
}



