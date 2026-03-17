package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
// import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
// import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.address.testutil.TypicalGamers.getTypicalBlockBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.address.model.BlockBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.gamer.Gamer;
import seedu.address.testutil.EditGamerDescriptorBuilder;
import seedu.address.testutil.GamerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Gamer editedGamer = new GamerBuilder().build();
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(editedGamer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedGamer));

        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedGamer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        Index indexLastGamer = Index.fromOneBased(model.getFilteredPersonList().size());
    //        Gamer lastGamer = model.getFilteredPersonList().get(indexLastGamer.getZeroBased());

    //        GamerBuilder personInList = new GamerBuilder(lastGamer);
    //        Gamer editedGamer = personInList.withName(VALID_NAME_BOB).build();

    //        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).build();
    //        EditCommand editCommand = new EditCommand(indexLastGamer, descriptor);

    //        String expectedMessage = String.format(
    //          EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
    //        expectedModel.setPerson(lastGamer, editedGamer);

    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    //    public void execute_noFieldSpecifiedUnfilteredList_success() {
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditGamerDescriptor());
    //        Gamer editedGamer = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

    //        String expectedMessage = String.format(
    //        EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());

    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    //    public void execute_filteredList_success() {
    //        showGamerAtIndex(model, INDEX_FIRST_PERSON);

    //        Gamer personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Gamer editedGamer = new GamerBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build());

    //        String expectedMessage = String.format(
    //        EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedGamer));

    //        Model expectedModel = new ModelManager(new BlockBook(model.getBlockBook()), new UserPrefs());
    //        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedGamer);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    @Test
    public void execute_duplicateGamerUnfilteredList_failure() {
        Gamer firstGamer = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(firstGamer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateGamerFilteredList_failure() {
        showGamerAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Gamer personInList = model.getBlockBook().getGamerList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditGamerDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidGamerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
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
        showGamerAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBlockBook().getGamerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditGamerDescriptor copyDescriptor = new EditGamerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
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
