package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Gamer;


/**
 * Adds a gamer to the BlockBook.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String COMMAND_WORD_WITH_ALIAS = "(a)dd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gamer to BlockBook.\n\n"
            + "Format: " + COMMAND_WORD_WITH_ALIAS + " gamertag/GAMERTAG "
            + "[name/NAME] [phone/PHONE] [email/EMAIL] "
            + "[server/SERVER] "
            + "[country/COUNTRY] "
            + "[region/REGION] [note/NOTE]\n\n"
            + "Example: " + COMMAND_WORD + " gamertag/ilovesteve name/Herobrine "
            + "phone/99999 email/brine@gmail.com "
            + "server/127.0.0.1:8080 "
            + "country/Singapore region/ASIA note/I hate steve";

    public static final String MESSAGE_SUCCESS = "Contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_GAMERTAG = "This gamertag is already used by someone in BlockBook.";
    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

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
        logger.info("Attempting to add gamer: " + toAdd);

        if (model.hasGamer(toAdd)) {
            logger.warning("Duplicate gamer tag detected: " + toAdd.getGamerTag());
            throw new CommandException(MESSAGE_DUPLICATE_GAMERTAG);
        }

        model.addGamer(toAdd);
        logger.info("Gamer added successfully: " + toAdd.getGamerTag());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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
