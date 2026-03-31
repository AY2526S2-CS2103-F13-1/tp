package seedu.blockbook.logic.commands;

import seedu.blockbook.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\tShows command usage instructions.\n"
            + "\n\tFormat: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "The user guide can be found in the menu bar. "
            + "These are the available commands (case-sensitive):";

    public static final String SEPARATOR = "\n-------------------------------------------------------------------\n\n";

    @Override
    public CommandResult execute(Model model) {
        StringBuilder outputString = new StringBuilder();
        outputString.append(SHOWING_HELP_MESSAGE + "\n");
        outputString.append(MESSAGE_USAGE + "\n\n");
        outputString.append(AddCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(EditCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(DeleteCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(FavouriteCommand.MESSAGE_USAGE + "\n");
        outputString.append(SEPARATOR);
        outputString.append(ListCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(ViewCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(SortCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(FindCommand.MESSAGE_USAGE + "\n");
        outputString.append(SEPARATOR);
        outputString.append(ClearCommand.MESSAGE_USAGE + "\n\n");
        outputString.append(ExitCommand.MESSAGE_USAGE);

        return new CommandResult(outputString.toString(), true, false);
    }
}

