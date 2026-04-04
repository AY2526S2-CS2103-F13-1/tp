package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_FAVOURITE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GAMERTAG_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_REGION_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_SERVER_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.EditGamerDescriptorBuilder;
import seedu.blockbook.testutil.GamerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private final Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Gamer gamerToEdit = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer editedGamer = new GamerBuilder(gamerToEdit)
                .withName(VALID_NAME_BOB)
                .withGamerTag(VALID_GAMERTAG_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withGroup(VALID_GROUP_BOB)
                .withServer(VALID_SERVER_BOB)
                .withFavourite(VALID_FAVOURITE_BOB)
                .withCountry(VALID_COUNTRY_BOB)
                .withRegion(VALID_REGION_BOB)
                .withNote(VALID_NOTE_BOB)
                .build();

        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(editedGamer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAMER_SUCCESS,
                Messages.format(editedGamer));

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setGamer(gamerToEdit, editedGamer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Gamer gamerToEdit = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer editedGamer = new GamerBuilder(gamerToEdit)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();

        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAMER_SUCCESS,
                Messages.format(editedGamer));

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setGamer(gamerToEdit, editedGamer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGamerUnfilteredList_failure() {
        Gamer secondGamer = model.getFilteredGamerList().get(INDEX_SECOND_GAMER.getZeroBased());
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder()
                .withGamerTag(secondGamer.getGamerTag().fullGamerTag)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_GAMER);
    }

    @Test
    public void execute_invalidGamerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of block book.
     */
    @Test
    public void execute_invalidGamerIndexFilteredList_failure() {
        showGamerAtIndex(model, INDEX_FIRST_GAMER);
        Index outOfBoundIndex = INDEX_SECOND_GAMER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBlockBook().getGamerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditGamerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_GAMER, DESC_AMY);

        EditGamerDescriptor copyDescriptor = new EditGamerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_GAMER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        assertTrue(standardCommand.equals(standardCommand));
        assertFalse(standardCommand.equals(null));
        assertFalse(standardCommand.equals(new ClearCommand(null)));
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_GAMER, DESC_AMY)));
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_GAMER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditGamerDescriptor editGamerDescriptor = new EditGamerDescriptor();
        EditCommand editCommand = new EditCommand(index, editGamerDescriptor);
        String expected = EditCommand.class.getCanonicalName()
                + "{index=" + index + ", editGamerDescriptor=" + editGamerDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
