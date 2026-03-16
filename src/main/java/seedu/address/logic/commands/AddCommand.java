package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gamer.Gamer;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to Blockbook. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GAMERTAG + "GAMERTAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Herobrine "
            + PREFIX_GAMERTAG + "ilovesteve";

    public static final String MESSAGE_SUCCESS = "Contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in Blockbook";

    private final Gamer toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Gamer gamer) {
        requireNonNull(gamer);
        toAdd = gamer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
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
