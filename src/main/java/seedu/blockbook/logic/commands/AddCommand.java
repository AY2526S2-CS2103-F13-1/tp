package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Adds a gamer to the BlockBook.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gamer to BlockBook. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GAMERTAG + "GAMERTAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Herobrine "
            + PREFIX_GAMERTAG + "ilovesteve";

    public static final String MESSAGE_SUCCESS = "Contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_GAMERTAG = "This gamertag is already used by someone in BlockBook.";

    private final Gamer toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Gamer}.
     */
    public AddCommand(Gamer gamer) {
        requireNonNull(gamer);
        toAdd = gamer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGamer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GAMERTAG);
        }

        model.addGamer(toAdd);
        String formattedContact = toAdd.getName() + ", " + toAdd.getGamerTag();
        return new CommandResult(String.format(MESSAGE_SUCCESS, formattedContact));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
