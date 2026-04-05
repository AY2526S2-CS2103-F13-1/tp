package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.GamerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        SortCommand sortCommand = new SortCommand(new ArrayList<>());

        assertCommandFailure(sortCommand, emptyModel, SortCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_defaultSort_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(new ArrayList<>());
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_DEFAULT_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_sortByName_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(List.of("name"));
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_SUCCESS, result.getFeedbackToUser());

        // Verify list is sorted by name (case-insensitive)
        List<Gamer> sortedList = model.getFilteredGamerList();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            String currentName = sortedList.get(i).getName().toString();
            String nextName = sortedList.get(i + 1).getName().toString();
            assertTrue(currentName.compareToIgnoreCase(nextName) <= 0,
                    "List should be sorted by name: " + currentName + " should come before " + nextName);
        }
    }

    @Test
    public void execute_sortByGamertag_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(List.of("gamertag"));
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_SUCCESS, result.getFeedbackToUser());

        // Verify list is sorted by gamertag
        List<Gamer> sortedList = model.getFilteredGamerList();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            String currentTag = sortedList.get(i).getGamerTag().toString();
            String nextTag = sortedList.get(i + 1).getGamerTag().toString();
            assertTrue(currentTag.compareToIgnoreCase(nextTag) <= 0,
                    "List should be sorted by gamertag: " + currentTag + " should come before " + nextTag);
        }
    }

    @Test
    public void execute_sortByMultipleAttributes_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(List.of("name", "gamertag"));
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_favouritesAlwaysFirst_success() throws CommandException {
        // Create a model with a mix of favourite and non-favourite gamers
        BlockBook blockBook = new BlockBook();
        Gamer nonFavGamer = new GamerBuilder().withName("Zack").withGamerTag("zack1")
                .withFavourite(false).build();
        Gamer favGamer = new GamerBuilder().withName("Alice").withGamerTag("alice1")
                .withFavourite(true).build();
        blockBook.addGamer(nonFavGamer);
        blockBook.addGamer(favGamer);
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        SortCommand sortCommand = new SortCommand(List.of("name"));
        sortCommand.execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        // Favourite gamer (Alice) should come first even though we added non-fav first
        assertEquals("Alice", sortedList.get(0).getName().toString());
        assertEquals("Zack", sortedList.get(1).getName().toString());
    }

    @Test
    public void execute_favouritesGroupedThenSortedByAttribute_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        Gamer favGamerB = new GamerBuilder().withName("Bob").withGamerTag("bob1")
                .withFavourite(true).build();
        Gamer favGamerA = new GamerBuilder().withName("Alice").withGamerTag("alice1")
                .withFavourite(true).build();
        Gamer nonFavGamerD = new GamerBuilder().withName("Dave").withGamerTag("dave1")
                .withFavourite(false).build();
        Gamer nonFavGamerC = new GamerBuilder().withName("Charlie").withGamerTag("charlie1")
                .withFavourite(false).build();
        blockBook.addGamer(favGamerB);
        blockBook.addGamer(nonFavGamerD);
        blockBook.addGamer(favGamerA);
        blockBook.addGamer(nonFavGamerC);
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        SortCommand sortCommand = new SortCommand(List.of("name"));
        sortCommand.execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        // Favourites sorted by name first, then non-favourites sorted by name
        assertEquals("Alice", sortedList.get(0).getName().toString());
        assertEquals("Bob", sortedList.get(1).getName().toString());
        assertEquals("Charlie", sortedList.get(2).getName().toString());
        assertEquals("Dave", sortedList.get(3).getName().toString());
    }

    @Test
    public void execute_invalidAttribute_throwsCommandException() {
        SortCommand sortCommand = new SortCommand(List.of("invalid"));

        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_INVALID_ATTRIBUTES);
    }

    @Test
    public void execute_sessionBasedSort_doesNotModifyStorage() throws CommandException {
        BlockBook originalBlockBook = new BlockBook(model.getBlockBook());

        SortCommand sortCommand = new SortCommand(List.of("name"));
        sortCommand.execute(model);

        // The underlying BlockBook data should remain unchanged
        assertEquals(originalBlockBook, model.getBlockBook());
    }

    @Test
    public void equals() {
        SortCommand sortByName = new SortCommand(List.of("name"));
        SortCommand sortByPhone = new SortCommand(List.of("phone"));
        SortCommand sortByNameCopy = new SortCommand(List.of("name"));
        SortCommand sortByNameAndPhone = new SortCommand(List.of("name", "phone"));
        SortCommand sortDefault = new SortCommand(new ArrayList<>());

        // same object -> returns true
        assertTrue(sortByName.equals(sortByName));

        // same values -> returns true
        assertTrue(sortByName.equals(sortByNameCopy));

        // different types -> returns false
        assertFalse(sortByName.equals(1));

        // null -> returns false
        assertFalse(sortByName.equals(null));

        // different attribute -> returns false
        assertFalse(sortByName.equals(sortByPhone));

        // different number of attributes -> returns false
        assertFalse(sortByName.equals(sortByNameAndPhone));

        // default vs specific -> returns false
        assertFalse(sortDefault.equals(sortByName));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand(List.of("name", "phone"));
        String expected = SortCommand.class.getCanonicalName() + "{attributes=[name, phone]}";
        assertEquals(expected, sortCommand.toString());
    }
}
