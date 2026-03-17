package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGamerAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.BlockBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

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
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies list resets a filtered list back to the full list.
     */
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showGamerAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
