package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;

public class HelpCommandTest {

    private static final String SEPARATOR = "------------------------------------------------------------------------";

    @Test
    public void execute_returnsExpectedHelpOutput() {
        Model model = new ModelManager();

        String expectedOutput = HelpCommand.SHOWING_HELP_MESSAGE + "\n"
                + HelpCommand.MESSAGE_USAGE + "\n\n"
                + AddCommand.MESSAGE_USAGE + "\n\n"
                + EditCommand.MESSAGE_USAGE + "\n\n"
                + DeleteCommand.MESSAGE_USAGE + "\n"
                + SEPARATOR + "\n"
                + FindCommand.MESSAGE_USAGE + "\n\n"
                + ListCommand.MESSAGE_USAGE + "\n"
                + SEPARATOR + "\n"
                + ClearCommand.MESSAGE_USAGE + "\n\n"
                + ExitCommand.MESSAGE_USAGE;

        CommandResult expectedCommandResult = new CommandResult(expectedOutput, false, false);

        assertEquals(expectedCommandResult, new HelpCommand().execute(model));
    }

    @Test
    public void execute_includesAllCommandUsageMessages() {
        Model model = new ModelManager();
        String output = new HelpCommand().execute(model).getFeedbackToUser();

        assertTrue(output.contains(HelpCommand.MESSAGE_USAGE));
        assertTrue(output.contains(AddCommand.MESSAGE_USAGE));
        assertTrue(output.contains(EditCommand.MESSAGE_USAGE));
        assertTrue(output.contains(DeleteCommand.MESSAGE_USAGE));
        assertTrue(output.contains(FindCommand.MESSAGE_USAGE));
        assertTrue(output.contains(ListCommand.MESSAGE_USAGE));
        assertTrue(output.contains(ClearCommand.MESSAGE_USAGE));
        assertTrue(output.contains(ExitCommand.MESSAGE_USAGE));
    }
}
