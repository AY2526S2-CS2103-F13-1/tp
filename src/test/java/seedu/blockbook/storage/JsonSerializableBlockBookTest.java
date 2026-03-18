package seedu.blockbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.blockbook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.exceptions.IllegalValueException;
import seedu.blockbook.commons.util.JsonUtil;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.testutil.TypicalGamers;

public class JsonSerializableBlockBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBlockBookTest");
    private static final Path TYPICAL_GAMERS_FILE = TEST_DATA_FOLDER.resolve("typicalGamerBlockBook.json");
    private static final Path INVALID_GAMER_FILE = TEST_DATA_FOLDER.resolve("invalidGamerBlockBook.json");
    private static final Path DUPLICATE_GAMER_FILE = TEST_DATA_FOLDER.resolve("duplicateGamerBlockBook.json");

    @Test
    public void toModelType_typicalGamersFile_success() throws Exception {
        JsonSerializableBlockBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_GAMERS_FILE,
                JsonSerializableBlockBook.class).get();
        BlockBook blockBookFromFile = dataFromFile.toModelType();
        BlockBook typicalGamersBlockBook = TypicalGamers.getTypicalBlockBook();
        assertEquals(blockBookFromFile, typicalGamersBlockBook);
    }

    @Test
    public void toModelType_invalidGamerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockBook dataFromFile = JsonUtil.readJsonFile(INVALID_GAMER_FILE,
                JsonSerializableBlockBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGamers_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GAMER_FILE,
                JsonSerializableBlockBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBlockBook.MESSAGE_DUPLICATE_GAMER,
                dataFromFile::toModelType);
    }

}


