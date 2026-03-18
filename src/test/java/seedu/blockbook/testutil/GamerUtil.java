package seedu.blockbook.testutil;

//import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;

import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.blockbook.model.gamer.Gamer;

/**
 * A utility class for Gamer.
 */
public class GamerUtil {

    /**
     * Returns an add command string for adding the {@code gamer}.
     */
    public static String getAddCommand(Gamer gamer) {
        return AddCommand.COMMAND_WORD + " " + getGamerDetails(gamer);
    }

    /**
     * Returns the part of command string for the given {@code gamer}'s details.
     */
    public static String getGamerDetails(Gamer gamer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + gamer.getName().fullName + " ");
        sb.append(PREFIX_GAMERTAG + gamer.getGamerTag().fullGamerTag + " ");
        // sb.append(PREFIX_PHONE + gamer.getPhone().value + " ");
        // sb.append(PREFIX_EMAIL + gamer.getEmail().value + " ");

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
