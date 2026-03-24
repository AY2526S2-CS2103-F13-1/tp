package seedu.blockbook.logic.commands;

import seedu.blockbook.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "The user guide can be found in the help menu above.\n"
            + "These are the available commands:\n";

    @Override
    public CommandResult execute(Model model) {
        StringBuilder outputString = new StringBuilder();
        outputString.append(SHOWING_HELP_MESSAGE);
        outputString.append(MESSAGE_USAGE + "\n\n");
        outputString.append(AddCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(EditCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(DeleteCommand.MESSAGE_USAGE + "\n\n");
//        outputString.append(ClearCommand.MESSAGE_USAGE + "\");
        outputString.append(FindCommand.MESSAGE_USAGE);
//        outputString.append(ListCommand.MESSAGE_USAGE + "");

        return new CommandResult(outputString.toString(), true, false);
    }
}

