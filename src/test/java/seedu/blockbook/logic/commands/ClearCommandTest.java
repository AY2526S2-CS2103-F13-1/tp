package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_firstAttempt_throwsConfirmationMessage() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        CommandException exception = assertThrows(CommandException.class, () -> new ClearCommand("").execute(model));
        extractConfirmationCode(exception);

        assertEquals(new ModelManager(getTypicalBlockBook(), new UserPrefs()), model);
    }

    @Test
    public void execute_nonEmptyBlockBookWithCorrectConfirmation_success() throws CommandException {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        CommandException exception = assertThrows(CommandException.class, () -> new ClearCommand("").execute(model));
        String generatedCode = extractConfirmationCode(exception);

        expectedModel.setBlockBook(new BlockBook());
        assertCommandSuccess(new ClearCommand(generatedCode), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBlockBookWithWrongConfirmation_throwsConfirmationMessage() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        CommandException firstException = assertThrows(
                CommandException.class, () -> new ClearCommand("").execute(model)
        );
        String generatedCode = extractConfirmationCode(firstException);
        String wrongCode = generatedCode.equals(" aaaaaa") ? " bbbbbb" : " aaaaaa";
        assertNotEquals(generatedCode, wrongCode);

        CommandException secondException = assertThrows(CommandException.class, () ->
                new ClearCommand(wrongCode).execute(model)
        );
        extractConfirmationCode(secondException);

        assertEquals(new ModelManager(getTypicalBlockBook(), new UserPrefs()), model);
    }

    private String extractConfirmationCode(CommandException exception) {
        String message = exception.getMessage();
        assertTrue(message.startsWith(ClearCommand.CONFIRMATION_MESSAGE));

        String confirmationCode = message.substring(ClearCommand.CONFIRMATION_MESSAGE.length());
        assertTrue(confirmationCode.matches(" [a-z0-9]{6}"));
        return confirmationCode;
    }
}
