package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;

public class ClearCommandTest {

    @BeforeEach
    public void setUp() {
        ClearCommand.confirmationCode = null;
    }

    @Test
    public void execute_firstAttempt_throwsConfirmationMessage() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        CommandException exception = assertThrows(CommandException.class, () -> new ClearCommand("").execute(model));

        assertTrue(exception.getMessage().startsWith(ClearCommand.CONFIRMATION_MESSAGE));
        assertNotNull(ClearCommand.confirmationCode);
        assertEquals(new ModelManager(getTypicalBlockBook(), new UserPrefs()), model);
    }

    @Test
    public void execute_nonEmptyBlockBookWithCorrectConfirmation_success() throws CommandException {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> new ClearCommand("").execute(model));
        String generatedCode = ClearCommand.confirmationCode;

        expectedModel.setBlockBook(new BlockBook());
        assertCommandSuccess(new ClearCommand(generatedCode), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(null, ClearCommand.confirmationCode);
    }

    @Test
    public void execute_nonEmptyBlockBookWithWrongConfirmation_throwsConfirmationMessage() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        ClearCommand.confirmationCode = " abc123";

        CommandException exception = assertThrows(CommandException.class, () -> new ClearCommand(" wrong12").execute(model));

        assertTrue(exception.getMessage().startsWith(ClearCommand.CONFIRMATION_MESSAGE));
        assertNotNull(ClearCommand.confirmationCode);
        assertEquals(new ModelManager(getTypicalBlockBook(), new UserPrefs()), model);
    }
}
