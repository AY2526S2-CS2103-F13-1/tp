package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;

/**
 * Lists all gamers in the BlockBook to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String COMMAND_WORD_WITH_ALIAS = "(l)ist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the gamers in your BlockBook.\n"
            + "\nFormat: " + COMMAND_WORD_WITH_ALIAS;

    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_GAMERS_LISTED_OVERVIEW;

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);


    /**
     * Lists all gamers and returns a generic success message when contacts exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.clearSort();
            model.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
            int gamerCount = model.getFilteredGamerList().size();
            if (gamerCount == 0) {
                return new CommandResult(Messages.MESSAGE_NO_CONTACTS);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Failed to list gamers.", e);
            throw new CommandException(Messages.MESSAGE_DISPLAY_CONTACTS_ERROR);
        }
    }
}


