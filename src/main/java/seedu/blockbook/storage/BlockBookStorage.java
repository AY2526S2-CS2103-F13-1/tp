package seedu.blockbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;

/**
 * Represents a storage for {@link BlockBook}.
 */
public interface BlockBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBlockBookFilePath();

    /**
     * Returns BlockBook data as a {@link ReadOnlyBlockBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyBlockBook> readBlockBook() throws DataLoadingException;

    /**
     * @see #getBlockBookFilePath()
     */
    Optional<ReadOnlyBlockBook> readBlockBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyBlockBook} to the storage.
     * @param blockBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBlockBook(ReadOnlyBlockBook blockBook) throws IOException;

    /**
     * @see #saveBlockBook(ReadOnlyBlockBook)
     */
    void saveBlockBook(ReadOnlyBlockBook blockBook, Path filePath) throws IOException;

}

