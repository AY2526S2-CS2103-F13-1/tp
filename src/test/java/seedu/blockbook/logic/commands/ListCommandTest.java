package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
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
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
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
        // EP: non-empty unfiltered list
        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies list resets a filtered list back to the full list.
     */
    @Test
    public void execute_listIsFiltered_showsEverything() {
        // EP: non-empty filtered list
        showGamerAtIndex(model, TypicalIndexes.INDEX_FIRST_GAMER);
        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_afterFind_listClearsFindFilterAndRestoresOriginalOrder() {
        // Mix strategy: apply find filter first, then verify list restores full insertion-order view
        ArrayList<Gamer> originalOrder = new ArrayList<>(model.getFilteredGamerList());
        FindCommand findCommand = new FindCommand(new AnyAttributeContainsKeywordsPredicate("Kurz"));
        findCommand.execute(model);
        assertNotEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));

        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));
    }

    /**
     * Verifies list resets any active sort and restores insertion order.
     */
    @Test
    public void execute_sortedList_resetsToInsertionOrder() {
        // EP: non-empty sorted list
        ArrayList<Gamer> originalOrder = new ArrayList<>(model.getFilteredGamerList());
        model.sortGamerList(Comparator.comparing((Gamer gamer) -> gamer.getName().toString()).reversed());
        assertNotEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));

        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_filteredAndSorted_resetsToFullInsertionOrder() {
        // Mix strategy: combine filtered state and sorted state
        ArrayList<Gamer> originalOrder = new ArrayList<>(model.getFilteredGamerList());
        model.sortGamerList(Comparator.comparing((Gamer gamer) -> gamer.getName().toString()).reversed());
        showGamerAtIndex(model, TypicalIndexes.INDEX_FIRST_GAMER);
        assertNotEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));

        expectedModel.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(originalOrder, new ArrayList<>(model.getFilteredGamerList()));
    }

    /**
     * Verifies list shows the no-contacts message when there are no contacts.
     */
    @Test
    public void execute_noContacts_showsMessage() {
        // EP: empty list
        Model emptyModel = new ModelManager(new BlockBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), emptyModel, Messages.MESSAGE_NO_CONTACTS, emptyModel);
    }

    @Test
    public void execute_singleContact_showsSuccess() {
        // BV: size = 1 (just above empty-list boundary)
        BlockBook singleContactBook = new BlockBook();
        singleContactBook.addGamer(ALICE);
        Model singleContactModel = new ModelManager(singleContactBook, new UserPrefs());
        assertCommandSuccess(new ListCommand(), singleContactModel, ListCommand.MESSAGE_SUCCESS, singleContactModel);
    }
}



