package seedu.address.testutil;

//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.address.model.gamer.Gamer;

/**
 * A utility class for Gamer.
 */
public class GamerUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Gamer person) {
        return AddCommand.COMMAND_WORD + " " + getGamerDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getGamerDetails(Gamer person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_GAMERTAG + person.getGamerTag().fullGamerTag + " ");
        // sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        // sb.append(PREFIX_EMAIL + person.getEmail().value + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditGamerDescriptor}'s details.
     */
    public static String getEditGamerDescriptorDetails(EditGamerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        // descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        // descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));

        return sb.toString();
    }
}
