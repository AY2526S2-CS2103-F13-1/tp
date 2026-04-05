package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.blockbook.testutil.GamerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FavouriteCommand}.
 */
public class FavouriteCommandTest {

    @Test
    public void execute_unfavouriteContact_success() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Gamer gamerToToggle = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());

        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_GAMER, false);

        Gamer updatedGamer = new GamerBuilder(gamerToToggle).withFavourite(false).build();
        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setGamer(gamerToToggle, updatedGamer);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_UNMARK_FAVOURITE_SUCCESS,
                formatContactSummary(updatedGamer));

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_favouriteContact_success() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Gamer gamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer unfavGamer = new GamerBuilder(gamer).withFavourite(false).build();
        model.setGamer(gamer, unfavGamer);

        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_GAMER, true);

        Gamer favGamer = new GamerBuilder(unfavGamer).withFavourite(true).build();
        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setGamer(unfavGamer, favGamer);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_MARK_FAVOURITE_SUCCESS,
                formatContactSummary(favGamer));

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_favouriteAlreadyFavourite_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Gamer gamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());

        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_GAMER, true);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_ALREADY_FAVOURITE,
                Messages.formatNullable(gamer.getName()));

        assertCommandFailure(favouriteCommand, model, expectedMessage);
    }

    @Test
    public void execute_unfavouriteAlreadyUnfavourite_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Gamer gamer = model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased());
        Gamer unfavGamer = new GamerBuilder(gamer).withFavourite(false).build();
        model.setGamer(gamer, unfavGamer);

        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_GAMER, false);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_ALREADY_UNFAVOURITE,
                Messages.formatNullable(unfavGamer.getName()));

        assertCommandFailure(favouriteCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        FavouriteCommand favouriteCommand = new FavouriteCommand(outOfBoundIndex, true);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        FavouriteCommand favouriteCommand = new FavouriteCommand(Index.fromOneBased(1), true);

        assertCommandFailure(favouriteCommand, emptyModel, FavouriteCommand.MESSAGE_EMPTY_CONTACT_LIST);
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_GAMER, true);
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(INDEX_SECOND_GAMER, true);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand favouriteFirstCommandCopy = new FavouriteCommand(INDEX_FIRST_GAMER, true);
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different mode -> returns false
        FavouriteCommand unfavouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_GAMER, false);
        assertFalse(favouriteFirstCommand.equals(unfavouriteFirstCommand));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different gamer -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        FavouriteCommand favouriteCommand = new FavouriteCommand(targetIndex, true);
        String expected = FavouriteCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", markFavourite=true}";
        assertEquals(expected, favouriteCommand.toString());
    }

    private static String formatContactSummary(Gamer gamer) {
        return String.format("\n Name: %s\n GamerTag: %s",
                Messages.formatNullable(gamer.getName()),
                Messages.formatNullable(gamer.getGamerTag()));
    }
}
