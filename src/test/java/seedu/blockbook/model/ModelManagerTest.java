package seedu.blockbook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.testutil.BlockBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new BlockBook(), new BlockBook(modelManager.getBlockBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setBlockBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setBlockBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setBlockBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setBlockBookFilePath(null));
    }

    @Test
    public void setBlockBookFilePath_validPath_setsBlockBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setBlockBookFilePath(path);
        assertEquals(path, modelManager.getBlockBookFilePath());
    }

    @Test
    public void setBlockBook_validBlockBook_replacesBlockBook() {
        BlockBook blockBook = new BlockBookBuilder().withGamer(ALICE).withGamer(BENSON).build();

        modelManager.setBlockBook(blockBook);

        assertEquals(blockBook, modelManager.getBlockBook());
    }

    @Test
    public void hasGamer_nullGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGamer(null));
    }

    @Test
    public void hasGamer_gamerNotInBlockBook_returnsFalse() {
        assertFalse(modelManager.hasGamer(ALICE));
    }

    @Test
    public void hasGamer_gamerInBlockBook_returnsTrue() {
        modelManager.addGamer(ALICE);
        assertTrue(modelManager.hasGamer(ALICE));
    }

    @Test
    public void addGamer_resetsFilteredListToShowAllGamers() {
        modelManager.addGamer(ALICE);
        modelManager.updateFilteredGamerList(gamer -> false);

        modelManager.addGamer(BENSON);

        assertEquals(2, modelManager.getFilteredGamerList().size());
        assertTrue(modelManager.getFilteredGamerList().contains(ALICE));
        assertTrue(modelManager.getFilteredGamerList().contains(BENSON));
    }

    @Test
    public void updateFilteredGamerList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredGamerList(null));
    }

    @Test
    public void updateFilteredGamerList_validPredicate_updatesFilteredList() {
        modelManager.addGamer(ALICE);
        modelManager.addGamer(BENSON);

        modelManager.updateFilteredGamerList(gamer -> gamer.equals(ALICE));

        assertEquals(1, modelManager.getFilteredGamerList().size());
        assertEquals(ALICE, modelManager.getFilteredGamerList().get(0));
    }

    @Test
    public void getFilteredGamerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGamerList().remove(0));
    }

    @Test
    public void sortGamerList_nullComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.sortGamerList(null));
    }

    @Test
    public void sortAndClearSort_validComparator_sortsAndRestoresInsertionOrder() {
        modelManager.addGamer(BENSON);
        modelManager.addGamer(ALICE);

        modelManager.sortGamerList(Comparator.comparing(gamer -> gamer.getName().toString()));
        assertEquals(ALICE, modelManager.getFilteredGamerList().get(0));
        assertEquals(BENSON, modelManager.getFilteredGamerList().get(1));

        modelManager.clearSort();
        assertEquals(BENSON, modelManager.getFilteredGamerList().get(0));
        assertEquals(ALICE, modelManager.getFilteredGamerList().get(1));
    }

    @Test
    public void equals() {
        BlockBook blockBook = new BlockBookBuilder().withGamer(ALICE).withGamer(BENSON).build();
        BlockBook differentBlockBook = new BlockBook();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(blockBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(blockBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different BlockBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentBlockBook, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredGamerList(gamer -> gamer.equals(ALICE));
        assertFalse(modelManager.equals(new ModelManager(blockBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBlockBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(blockBook, differentUserPrefs)));
    }
}
