package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;

public class HelpCommandTest {
    @Test
    public void execute_returnsExpectedHelpOutput() {
        Model model = new ModelManager();

        String expectedOutput = HelpCommand.SHOWING_HELP_MESSAGE + "\n\n"
                + HelpCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + AddCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + EditCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + DeleteCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + FavouriteCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + ListCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + ViewCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + SortCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + FindCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + GroupCreateCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + GroupEditCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + GroupAddCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + GroupRemoveCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + ClearCommand.MESSAGE_USAGE
                + HelpCommand.SEPARATOR
                + ExitCommand.MESSAGE_USAGE;

        CommandResult expectedCommandResult = new CommandResult(expectedOutput, true, false);

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
        assertTrue(output.contains(FavouriteCommand.MESSAGE_USAGE));

        assertTrue(output.contains(ListCommand.MESSAGE_USAGE));
        assertTrue(output.contains(ViewCommand.MESSAGE_USAGE));
        assertTrue(output.contains(SortCommand.MESSAGE_USAGE));
        assertTrue(output.contains(FindCommand.MESSAGE_USAGE));

        assertTrue(output.contains(GroupCreateCommand.MESSAGE_USAGE));
        assertTrue(output.contains(GroupAddCommand.MESSAGE_USAGE));
        assertTrue(output.contains(GroupRemoveCommand.MESSAGE_USAGE));

        assertTrue(output.contains(ClearCommand.MESSAGE_USAGE));
        assertTrue(output.contains(ExitCommand.MESSAGE_USAGE));
    }
}
