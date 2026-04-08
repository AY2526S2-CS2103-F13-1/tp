package seedu.blockbook.logic.commands;

import seedu.blockbook.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_ALIAS = "?";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows command usage instructions.\n"
            + "\nFormat: " + COMMAND_ALIAS + " or " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "The user guide can be found in the menu bar. "
            + "These are the available commands (case-sensitive):";

    public static final String SEPARATOR = "\n\n---------------------------------------------------\n\n";

    @Override
    public CommandResult execute(Model model) {
        StringBuilder outputString = new StringBuilder();
        outputString.append(SHOWING_HELP_MESSAGE + "\n\n");
        outputString.append(MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(AddCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(EditCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(DeleteCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(FavouriteCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(ListCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(ViewCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(SortCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(FindCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupCreateCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupEditCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupAddCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupRemoveCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupViewCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(GroupNukeCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(ClearCommand.MESSAGE_USAGE);
        outputString.append(SEPARATOR);
        outputString.append(ExitCommand.MESSAGE_USAGE);

        return new CommandResult(outputString.toString(), true, false);
    }
}
