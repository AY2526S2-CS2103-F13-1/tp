package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        SortCommand sortCommand = new SortCommand(new ArrayList<>());

        assertCommandFailure(sortCommand, emptyModel, SortCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_sortByName_success() throws CommandException {
        assertSortByStringAttribute("name",
                GamerBuilder::withName, g -> g.getName().toString(),
                "Alice", "Ben", "Charlie");
    }

    @Test
    public void execute_defaultSort_sortsByGamertag() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("zulu").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("alpha").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("mike").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        CommandResult result = new SortCommand(new ArrayList<>()).execute(testModel);
        assertEquals(SortCommand.MESSAGE_SORT_DEFAULT_SUCCESS, result.getFeedbackToUser());

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("alpha", sortedList.get(0).getGamerTag().toString());
        assertEquals("mike", sortedList.get(1).getGamerTag().toString());
        assertEquals("zulu", sortedList.get(2).getGamerTag().toString());
    }

    @Test
    public void execute_sortByGamertag_success() throws CommandException {
        assertSortByStringAttribute("gamertag",
                GamerBuilder::withGamerTag, g -> g.getGamerTag().toString(),
                "alpha", "mike", "zulu");
    }

    @Test
    public void execute_sortByPhone_success() throws CommandException {
        assertSortByStringAttribute("phone",
                GamerBuilder::withPhone, g -> g.getPhone().toString(),
                "11111111", "55555555", "99999999");
    }

    @Test
    public void execute_sortByEmail_success() throws CommandException {
        assertSortByStringAttribute("email",
                GamerBuilder::withEmail, g -> g.getEmail().toString(),
                "adam@test.com", "mike@test.com", "zulu@test.com");
    }

    @Test
    public void execute_sortByGroup_success() throws CommandException {
        assertSortByStringAttribute("group",
                GamerBuilder::withGroup, g -> g.getGroup().toString(),
                "Alpha", "Mike", "Zeta");
    }

    @Test
    public void execute_sortByMultipleGroups_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        // Gamer with groups [Zeta, Alpha] → sort key "Alpha, Zeta"
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1")
                .withGroups("Zeta", "Alpha").build());
        // Gamer with groups [Beta] → sort key "Beta"
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1")
                .withGroups("Beta").build());
        // Gamer with groups [Alpha, Beta] → sort key "Alpha, Beta"
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1")
                .withGroups("Alpha", "Beta").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        new SortCommand(List.of("group")).execute(testModel);

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        // "Alpha, Beta" < "Alpha, Zeta" < "Beta"
        assertEquals("c1", sortedList.get(0).getGamerTag().toString());
        assertEquals("a1", sortedList.get(1).getGamerTag().toString());
        assertEquals("b1", sortedList.get(2).getGamerTag().toString());
    }

    @Test
    public void execute_sortByServer_success() throws CommandException {
        assertSortByStringAttribute("server",
                GamerBuilder::withServer, g -> g.getServer().toString(),
                "eu-central", "sea", "us-west");
    }

    @Test
    public void execute_sortByCountry_success() throws CommandException {
        assertSortByStringAttribute("country",
                GamerBuilder::withCountry, g -> g.getCountry().toString(),
                "DE", "JP", "US");
    }

    @Test
    public void execute_sortByRegion_success() throws CommandException {
        assertSortByStringAttribute("region",
                GamerBuilder::withRegion, g -> g.getRegion().toString(),
                "EU", "NA", "SA");
    }

    @Test
    public void execute_sortByNote_success() throws CommandException {
        assertSortByStringAttribute("note",
                GamerBuilder::withNote, g -> g.getNote().toString(),
                "Alpha note", "Mike note", "Zulu note");
    }

    @Test
    public void execute_sortByFavourite_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").withFavourite(false).build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").withFavourite(true).build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1").withFavourite(false).build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        CommandResult result = new SortCommand(List.of("favourite")).execute(testModel);
        assertEquals(String.format(SortCommand.MESSAGE_SORT_SUCCESS, "favourite"), result.getFeedbackToUser());

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertTrue(sortedList.get(0).getFavourite().isFav());
        assertFalse(sortedList.get(1).getFavourite().isFav());
        assertFalse(sortedList.get(2).getFavourite().isFav());
    }

    @Test
    public void execute_sortByMultipleAttributes_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("Alex").withGamerTag("t1").withPhone("99999999").build());
        blockBook.addGamer(new GamerBuilder().withName("Alex").withGamerTag("t2").withPhone("11111111").build());
        blockBook.addGamer(new GamerBuilder().withName("Ben").withGamerTag("t3").withPhone("55555555").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        CommandResult result = new SortCommand(List.of("name", "phone")).execute(testModel);
        assertEquals(String.format(SortCommand.MESSAGE_SORT_SUCCESS, "name, phone"), result.getFeedbackToUser());

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals("Alex", sortedList.get(0).getName().toString());
        assertEquals("11111111", sortedList.get(0).getPhone().toString());
        assertEquals("Alex", sortedList.get(1).getName().toString());
        assertEquals("99999999", sortedList.get(1).getPhone().toString());
        assertEquals("Ben", sortedList.get(2).getName().toString());
    }

    @Test
    public void execute_sortByFavouriteThenGroup_success() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1")
                .withFavourite(false).withGroup("Alpha").build());
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1")
                .withFavourite(true).withGroup("Zeta").build());
        blockBook.addGamer(new GamerBuilder().withName("C").withGamerTag("c1")
                .withFavourite(true).withGroup("Alpha").build());
        blockBook.addGamer(new GamerBuilder().withName("D").withGamerTag("d1")
                .withFavourite(false).withGroup("Zeta").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        CommandResult result = new SortCommand(List.of("favourite", "group")).execute(testModel);
        assertEquals(String.format(SortCommand.MESSAGE_SORT_SUCCESS, "favourite, group"),
                result.getFeedbackToUser());

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        // Favourites first, then sorted by group within each group
        assertTrue(sortedList.get(0).getFavourite().isFav());
        assertEquals("Alpha", sortedList.get(0).getGroup().toString());
        assertTrue(sortedList.get(1).getFavourite().isFav());
        assertEquals("Zeta", sortedList.get(1).getGroup().toString());
        assertFalse(sortedList.get(2).getFavourite().isFav());
        assertEquals("Alpha", sortedList.get(2).getGroup().toString());
        assertFalse(sortedList.get(3).getFavourite().isFav());
        assertEquals("Zeta", sortedList.get(3).getGroup().toString());
    }

    @Test
    public void execute_sessionBasedSort_doesNotModifyStorage() throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(new GamerBuilder().withName("B").withGamerTag("b1").build());
        blockBook.addGamer(new GamerBuilder().withName("A").withGamerTag("a1").build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        BlockBook originalBlockBook = new BlockBook(testModel.getBlockBook());

        new SortCommand(List.of("name")).execute(testModel);

        // The underlying BlockBook data should remain unchanged
        assertEquals(originalBlockBook, testModel.getBlockBook());
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

    /**
     * Helper that verifies sorting by a single string attribute.
     * Values are provided in expected sorted order; gamers are added in reverse
     * to ensure the sort actually reorders them.
     */
    private void assertSortByStringAttribute(String attribute,
            BiFunction<GamerBuilder, String, GamerBuilder> withAttribute,
            Function<Gamer, String> getAttribute,
            String expectedFirst, String expectedSecond, String expectedThird) throws CommandException {
        BlockBook blockBook = new BlockBook();
        blockBook.addGamer(withAttribute.apply(
                new GamerBuilder().withName("A").withGamerTag("a1"), expectedThird).build());
        blockBook.addGamer(withAttribute.apply(
                new GamerBuilder().withName("B").withGamerTag("b1"), expectedFirst).build());
        blockBook.addGamer(withAttribute.apply(
                new GamerBuilder().withName("C").withGamerTag("c1"), expectedSecond).build());
        Model testModel = new ModelManager(blockBook, new UserPrefs());

        CommandResult result = new SortCommand(List.of(attribute)).execute(testModel);
        assertEquals(String.format(SortCommand.MESSAGE_SORT_SUCCESS, attribute), result.getFeedbackToUser());

        List<Gamer> sortedList = testModel.getFilteredGamerList();
        assertEquals(expectedFirst, getAttribute.apply(sortedList.get(0)));
        assertEquals(expectedSecond, getAttribute.apply(sortedList.get(1)));
        assertEquals(expectedThird, getAttribute.apply(sortedList.get(2)));
    }
}
