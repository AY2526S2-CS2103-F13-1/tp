package seedu.blockbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.ReadOnlyUserPrefs;
import seedu.blockbook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends BlockBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getBlockBookFilePath();

    @Override
    Optional<ReadOnlyBlockBook> readBlockBook() throws DataLoadingException;

    @Override
    void saveBlockBook(ReadOnlyBlockBook blockBook) throws IOException;

}

