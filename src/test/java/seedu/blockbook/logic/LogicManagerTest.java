package seedu.blockbook.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.blockbook.logic.Messages.MESSAGE_MULTIPLE_INDEXES_INVALID;
import static seedu.blockbook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.blockbook.logic.commands.CommandTestUtil.GAMERTAG_DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_GAMERTAG_AMY;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.commands.CommandResult;
import seedu.blockbook.logic.commands.ListCommand;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.storage.JsonBlockBookStorage;
import seedu.blockbook.storage.JsonUserPrefsStorage;
import seedu.blockbook.storage.StorageManager;
import seedu.blockbook.testutil.GamerBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonBlockBookStorage blockBookStorage =
                new JsonBlockBookStorage(temporaryFolder.resolve("contacts.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(blockBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        model.addGamer(new GamerBuilder(AMY).build());
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_MULTIPLE_INDEXES_INVALID);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, Messages.MESSAGE_NO_CONTACTS, model);
    }

    // @Test
    // public void execute_storageThrowsIoException_throwsCommandException() {
    // assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
    // LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    // }

    // @Test
    // public void execute_storageThrowsAdException_throwsCommandException() {
    // assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
    // LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    // }

    @Test
    public void getFilteredGamerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredGamerList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e               the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with a BlockBookStorage that throws the IOException e when saving
        JsonBlockBookStorage blockBookStorage = new JsonBlockBookStorage(prefPath) {
            @Override
            public void saveBlockBook(ReadOnlyBlockBook blockBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(blockBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveBlockBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GAMERTAG_DESC_AMY;
        Gamer expectedGamer = new GamerBuilder().withName(VALID_NAME_AMY).withGamerTag(VALID_GAMERTAG_AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addGamer(expectedGamer);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
