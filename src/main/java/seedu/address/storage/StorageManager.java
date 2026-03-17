package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBlockBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BlockBookStorage blockBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
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


    // ================ AddressBook methods ==============================

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
    public void saveBlockBook(ReadOnlyBlockBook addressBook) throws IOException {
        saveBlockBook(addressBook, blockBookStorage.getBlockBookFilePath());
    }

    @Override
    public void saveBlockBook(ReadOnlyBlockBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        blockBookStorage.saveBlockBook(addressBook, filePath);
    }

}
