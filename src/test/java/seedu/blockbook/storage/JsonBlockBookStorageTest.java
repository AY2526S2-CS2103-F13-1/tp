package seedu.blockbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
import static seedu.blockbook.testutil.TypicalGamers.HOON;
import static seedu.blockbook.testutil.TypicalGamers.IDA;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;

public class JsonBlockBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBlockBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBlockBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBlockBook(null));
    }

    private java.util.Optional<ReadOnlyBlockBook> readBlockBook(String filePath) throws Exception {
        return new JsonBlockBookStorage(Paths.get(filePath)).readBlockBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBlockBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readBlockBook("notJsonFormatBlockBook.json"));
    }

    @Test
    public void readBlockBook_invalidGamerBlockBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readBlockBook("invalidGamerBlockBook.json"));
    }

    @Test
    public void readBlockBook_invalidAndValidGamerBlockBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readBlockBook("invalidAndValidGamerBlockBook.json"));
    }

    @Test
    public void readAndSaveBlockBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBlockBook.json");
        BlockBook original = getTypicalBlockBook();
        JsonBlockBookStorage jsonBlockBookStorage = new JsonBlockBookStorage(filePath);

        // Save in new file and read back
        jsonBlockBookStorage.saveBlockBook(original, filePath);
        ReadOnlyBlockBook readBack = jsonBlockBookStorage.readBlockBook(filePath).get();
        assertEquals(original, new BlockBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addGamer(HOON);
        original.removeGamer(ALICE);
        jsonBlockBookStorage.saveBlockBook(original, filePath);
        readBack = jsonBlockBookStorage.readBlockBook(filePath).get();
        assertEquals(original, new BlockBook(readBack));

        // Save and read without specifying file path
        original.addGamer(IDA);
        jsonBlockBookStorage.saveBlockBook(original); // file path not specified
        readBack = jsonBlockBookStorage.readBlockBook().get(); // file path not specified
        assertEquals(original, new BlockBook(readBack));

    }

    @Test
    public void saveBlockBook_nullBlockBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBlockBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code blockBook} at the specified {@code filePath}.
     */
    private void saveBlockBook(ReadOnlyBlockBook blockBook, String filePath) {
        try {
            new JsonBlockBookStorage(Paths.get(filePath))
                    .saveBlockBook(blockBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBlockBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBlockBook(new BlockBook(), null));
    }
}


