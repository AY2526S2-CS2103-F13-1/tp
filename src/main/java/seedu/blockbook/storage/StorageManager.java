package seedu.blockbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.ReadOnlyUserPrefs;
import seedu.blockbook.model.UserPrefs;

/**
 * Manages storage of BlockBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BlockBookStorage blockBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code BlockBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(BlockBookStorage blockBookStorage, UserPrefsStorage userPrefsStorage) {
        this.blockBookStorage = blockBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ BlockBook methods ==============================

    @Override
    public Path getBlockBookFilePath() {
        return blockBookStorage.getBlockBookFilePath();
    }

    @Override
    public Optional<ReadOnlyBlockBook> readBlockBook() throws DataLoadingException {
        return readBlockBook(blockBookStorage.getBlockBookFilePath());
    }

    @Override
    public Optional<ReadOnlyBlockBook> readBlockBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return blockBookStorage.readBlockBook(filePath);
    }

    @Override
    public void saveBlockBook(ReadOnlyBlockBook blockBook) throws IOException {
        saveBlockBook(blockBook, blockBookStorage.getBlockBookFilePath());
    }

    @Override
    public void saveBlockBook(ReadOnlyBlockBook blockBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        blockBookStorage.saveBlockBook(blockBook, filePath);
    }

}

