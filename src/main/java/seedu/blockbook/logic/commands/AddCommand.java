package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

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

    //    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gamer to BlockBook. "
    //            + "Parameters: "
    //            + PREFIX_NAME + "NAME "
    //            + PREFIX_GAMERTAG + "GAMERTAG\n"
    //            + "Example: " + COMMAND_WORD + " "
    //            + PREFIX_NAME + "Herobrine "
    //            + PREFIX_GAMERTAG + "ilovesteve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gamer to BlockBook.\n"
            + "Format: add gamertag/GAMERTAG "
            + "[name/NAME] [phone/PHONE] [email/EMAIL] "
            + "[group/GROUP] [server/SERVER] "
            + "[favourite/FAVOURITE] [country/COUNTRY] "
            + "[region/REGION] [note/NOTE]\n"
            + "Example: add gamertag/ilovesteve name/Herobrine "
            + "phone/99999 email/brine@gmail.com "
            + "group/DestroySteve server/127.0.0.1:8080 favourite/fav "
            + "country/Singapore region/SEA note/I hate steve";

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
        //        String formattedContact = toAdd.getName() + ", " + toAdd.getGamerTag();
        //        String formattedContact = "Name: " + toAdd.getName()
        //                + " Gamertag: " + toAdd.getGamerTag()
        //                + " Phone: " + toAdd.getPhone()
        //                + " Email: " + toAdd.getEmail()
        //                + " Group: " + toAdd.getGroup()
        //                + " Server: " + toAdd.getServer()
        //                + " Favourite: " + toAdd.getFavourite()
        //                + " Country: " + toAdd.getCountry()
        //                + " Region: " + toAdd.getRegion()
        //                + " Note: " + toAdd.getNote();
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
