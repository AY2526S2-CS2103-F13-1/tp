package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;

/**
 * Clears the BlockBook.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes every gamer from BlockBook."
            + "CANNOT BE UNDONE!\nFormat: clear";
    public static final String MESSAGE_SUCCESS = "BlockBook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBlockBook(new BlockBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

