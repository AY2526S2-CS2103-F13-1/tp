package seedu.blockbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonBlockBookStorage blockBookStorage = new JsonBlockBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(blockBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void blockBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonBlockBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonBlockBookStorageTest} class.
         */
        BlockBook original = getTypicalBlockBook();
        storageManager.saveBlockBook(original);
        ReadOnlyBlockBook retrieved = storageManager.readBlockBook().get();
        assertEquals(original, new BlockBook(retrieved));
    }

    @Test
    public void getBlockBookFilePath() {
        assertNotNull(storageManager.getBlockBookFilePath());
    }

}

