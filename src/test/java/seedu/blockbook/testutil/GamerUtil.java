package seedu.blockbook.testutil;

import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;
//
//import java.util.Set;

import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

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
        sb.append(PREFIX_NAME + gamer.getName().toString() + " ");
        sb.append(PREFIX_GAMERTAG + gamer.getGamerTag().toString() + " ");
        if (gamer.getPhone() != null) {
            sb.append(PREFIX_PHONE + gamer.getPhone().toString() + " ");
        }
        if (gamer.getEmail() != null) {
            sb.append(PREFIX_EMAIL + gamer.getEmail().toString() + " ");
        }
        if (gamer.getServer() != null) {
            sb.append(PREFIX_SERVER + gamer.getServer().toString() + " ");
        }
        if (gamer.getCountry() != null) {
            sb.append(PREFIX_COUNTRY + gamer.getCountry().toString() + " ");
        }
        if (gamer.getRegion() != null) {
            sb.append(PREFIX_REGION + gamer.getRegion().toString() + " ");
        }
        if (gamer.getNote() != null) {
            sb.append(PREFIX_NOTE + gamer.getNote().toString() + " ");
        }

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditGamerDescriptor}'s details.
     */
    public static String getEditGamerDescriptorDetails(EditGamerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getGamerTag().ifPresent(gamerTag -> sb.append(PREFIX_GAMERTAG).append(gamerTag.toString())
                .append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.toString()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.toString()).append(" "));
        descriptor.getServer().ifPresent(server -> sb.append(PREFIX_SERVER).append(server.toString()).append(" "));
        descriptor.getCountry().ifPresent(country -> sb.append(PREFIX_COUNTRY).append(country.toString()).append(" "));
        descriptor.getRegion().ifPresent(region -> sb.append(PREFIX_REGION).append(region.toString()).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.toString()).append(" "));

        return sb.toString();
    }

    /**
     * Returns a gamer matching what the add command parser can reconstruct.
     * Groups and favourite are not accepted by add, so they are defaulted.
     */
    public static Gamer getAddCommandGamer(Gamer gamer) {
        return new Gamer(
                gamer.getName(),
                gamer.getGamerTag(),
                gamer.getPhone(),
                gamer.getEmail(),
                java.util.List.<Group>of(),
                gamer.getServer(),
                new Favourite(false),
                gamer.getCountry(),
                gamer.getRegion(),
                gamer.getNote()
        );
    }
}
