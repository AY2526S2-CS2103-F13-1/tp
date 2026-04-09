package seedu.blockbook.storage;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.commons.exceptions.IllegalValueException;
import seedu.blockbook.commons.util.FileUtil;
import seedu.blockbook.commons.util.JsonUtil;
import seedu.blockbook.model.ReadOnlyBlockBook;

/**
 * A class to access BlockBook data stored as a json file on the hard disk.
 */
public class JsonBlockBookStorage implements BlockBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBlockBookStorage.class);

    private Path filePath;

    public JsonBlockBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBlockBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBlockBook> readBlockBook() throws DataLoadingException {
        return readBlockBook(filePath);
    }

    /**
     * Similar to {@link #readBlockBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyBlockBook> readBlockBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        Optional<JsonSerializableBlockBook> jsonBlockBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableBlockBook.class);
        if (!jsonBlockBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBlockBook.get().toModelType());
        } catch (Exception ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveBlockBook(ReadOnlyBlockBook blockBook) throws IOException {
        saveBlockBook(blockBook, filePath);
    }

    /**
     * Similar to {@link #saveBlockBook(ReadOnlyBlockBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBlockBook(ReadOnlyBlockBook blockBook, Path filePath) throws IOException {
        requireNonNull(blockBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBlockBook(blockBook), filePath);
    }

}

