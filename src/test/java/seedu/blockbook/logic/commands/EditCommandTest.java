package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_SECOND_GAMER;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.testutil.EditGamerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

    // @Test
    // public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //     Gamer editedGamer = new GamerBuilder().build();
    //     EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(editedGamer).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER, descriptor);
    //
    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer));
    //
    //     Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
    //     expectedModel.setGamer(model.getFilteredGamerList().get(0), editedGamer);
    //
    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        Index indexLastGamer = Index.fromOneBased(model.getFilteredGamerList().size());
    //        Gamer lastGamer = model.getFilteredGamerList().get(indexLastGamer.getZeroBased());

    //        GamerBuilder gamerInList = new GamerBuilder(lastGamer);
    //        Gamer editedGamer = gamerInList.withName(VALID_NAME_BOB).build();

    //        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).build();
    //        EditCommand editCommand = new EditCommand(indexLastGamer, descriptor);

    //        String expectedMessage = String.format(
    //          EditCommand.MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
    //        expectedModel.setGamer(lastGamer, editedGamer);

    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    //    public void execute_noFieldSpecifiedUnfilteredList_success() {
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER, new EditGamerDescriptor());
    //        Gamer editedGamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());

    //        String expectedMessage = String.format(
    //        EditCommand.MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());

    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    //    public void execute_filteredList_success() {
    //        showGamerAtIndex(model, INDEX_FIRST_GAMER);

    //        Gamer gamerInFilteredList = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
    //        Gamer editedGamer = new GamerBuilder(gamerInFilteredList).withName(VALID_NAME_BOB).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER,
    //                new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build());

    //        String expectedMessage = String.format(
    //        EditCommand.MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
    //        expectedModel.setGamer(model.getFilteredGamerList().get(0), editedGamer);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    /*
    @Test
    public void execute_duplicateGamerUnfilteredList_failure() {
        Gamer firstGamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(firstGamer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_GAMER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_GAMER);
    }


    @Test
    public void execute_duplicateGamerFilteredList_failure() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);

        // edit gamer in filtered list into a duplicate in BlockBook
        Gamer gamerInList = model.getBlockBook().getGamerList().get(INDEX_SECOND_GAMER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER,
                new EditGamerDescriptorBuilder(gamerInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_GAMER);
    }
    */
    @Test
    public void execute_invalidGamerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGamerIndexFilteredList_failure() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);
        Index outOfBoundIndex = INDEX_SECOND_GAMER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBlockBook().getGamerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_GAMER, DESC_AMY);

        // same values -> returns true
        EditGamerDescriptor copyDescriptor = new EditGamerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_GAMER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_GAMER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_GAMER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditGamerDescriptor editGamerDescriptor = new EditGamerDescriptor();
        EditCommand editCommand = new EditCommand(index, editGamerDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editGamerDescriptor="
                + editGamerDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}



