package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

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
     * Verifies list succeeds when the list is not filtered.
     */
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies list resets a filtered list back to the full list.
     */
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showGamerAtIndex(model, TypicalIndexes.INDEX_FIRST_GAMER);
        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies list resets any active sort and restores insertion order.
     */
    @Test
    public void execute_sortedList_resetsToInsertionOrder() {
        ArrayList<Gamer> originalOrder = new ArrayList<>(model.getFilteredGamerList());
        model.sortGamerList(Comparator.comparing((Gamer gamer) -> gamer.getName().toString()).reversed());
        assertNotEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));

        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies list shows the no-contacts message when there are no contacts.
     */
    @Test
    public void execute_noContacts_showsMessage() {
        Model emptyModel = new ModelManager(new BlockBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), emptyModel, Messages.MESSAGE_NO_CONTACTS, emptyModel);
    }
}



