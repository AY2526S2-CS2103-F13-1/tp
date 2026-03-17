package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Lists all gamers in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_GAMERS_LISTED_OVERVIEW;


    /**
     * Lists all gamers and returns a generic success message when contacts exist.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            int gamerCount = model.getFilteredPersonList().size();
            if (gamerCount == 0) {
                return new CommandResult(Messages.MESSAGE_NO_CONTACTS);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (RuntimeException e) {
            return new CommandResult(Messages.MESSAGE_DISPLAY_CONTACTS_ERROR);
        }
    }
}
