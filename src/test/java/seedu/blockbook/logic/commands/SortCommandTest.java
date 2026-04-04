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
    public void execute_defaultSort_sortsByGamertag() throws CommandException {
        SortCommand sortCommand = new SortCommand(new ArrayList<>());
        sortCommand.execute(model);

        // Verify list is sorted by gamertag (default)
        List<Gamer> sortedList = model.getFilteredGamerList();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            String currentTag = sortedList.get(i).getGamerTag().toString();
            String nextTag = sortedList.get(i + 1).getGamerTag().toString();
            assertTrue(currentTag.compareToIgnoreCase(nextTag) <= 0,
                    "List should be sorted by gamertag: " + currentTag + " should come before " + nextTag);
        }
    }

    @Test
    public void execute_sortByGamertag_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(List.of("gamertag"));
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_SUCCESS, result.getFeedbackToUser());

        List<Gamer> sortedList = model.getFilteredGamerList();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            String currentTag = sortedList.get(i).getGamerTag().toString();
            String nextTag = sortedList.get(i + 1).getGamerTag().toString();
            assertTrue(currentTag.compareToIgnoreCase(nextTag) <= 0,
                    "List should be sorted by gamertag: " + currentTag + " should come before " + nextTag);
        }
    }

    @Test
    public void execute_sortByPhone_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withPhone("99999999").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withPhone("11111111").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withPhone("55555555").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("phone")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("11111111", sortedList.get(0).getPhone().toString());
        assertEquals("55555555", sortedList.get(1).getPhone().toString());
        assertEquals("99999999", sortedList.get(2).getPhone().toString());
    }

    @Test
    public void execute_sortByEmail_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withEmail("zulu@test.com").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withEmail("adam@test.com").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withEmail("mike@test.com").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("email")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("adam@test.com", sortedList.get(0).getEmail().toString());
        assertEquals("mike@test.com", sortedList.get(1).getEmail().toString());
        assertEquals("zulu@test.com", sortedList.get(2).getEmail().toString());
    }

    @Test
    public void execute_sortByGroup_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withGroup("Zeta").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withGroup("Alpha").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withGroup("Mike").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("group")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("Alpha", sortedList.get(0).getGroup().toString());
        assertEquals("Mike", sortedList.get(1).getGroup().toString());
        assertEquals("Zeta", sortedList.get(2).getGroup().toString());
    }

    @Test
    public void execute_sortByServer_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withServer("us-west").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withServer("eu-central").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withServer("sea").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("server")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("eu-central", sortedList.get(0).getServer().toString());
        assertEquals("sea", sortedList.get(1).getServer().toString());
        assertEquals("us-west", sortedList.get(2).getServer().toString());
    }

    @Test
    public void execute_sortByCountry_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withCountry("US").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withCountry("DE").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withCountry("JP").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("country")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("DE", sortedList.get(0).getCountry().toString());
        assertEquals("JP", sortedList.get(1).getCountry().toString());
        assertEquals("US", sortedList.get(2).getCountry().toString());
    }

    @Test
    public void execute_sortByRegion_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withRegion("SA").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withRegion("EU").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withRegion("NA").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("region")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("EU", sortedList.get(0).getRegion().toString());
        assertEquals("NA", sortedList.get(1).getRegion().toString());
        assertEquals("SA", sortedList.get(2).getRegion().toString());
    }

    @Test
    public void execute_sortByNote_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withNote("Zulu note").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withNote("Alpha note").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withNote("Mike note").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("note")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("Alpha note", sortedList.get(0).getNote().toString());
        assertEquals("Mike note", sortedList.get(1).getNote().toString());
        assertEquals("Zulu note", sortedList.get(2).getNote().toString());
    }

    @Test
    public void execute_sortByFavourite_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withFavourite("unfav").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withFavourite("fav").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withFavourite("unfav").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("favourite")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        // Favourites should come before non-favourites
        assertTrue(sortedList.get(0).getFavourite().isFav());
        assertFalse(sortedList.get(1).getFavourite().isFav());
        assertFalse(sortedList.get(2).getFavourite().isFav());
    }

    @Test
    public void execute_sortByMultipleAttributes_success() throws CommandException {
        SortCommand sortCommand = new SortCommand(List.of("name", "phone"));
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SORT_SUCCESS, result.getFeedbackToUser());
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
