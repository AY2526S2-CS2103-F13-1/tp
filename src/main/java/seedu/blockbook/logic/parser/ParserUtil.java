package seedu.blockbook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.StringUtil;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a series of space separated {@code oneBasedIndexes} into an {@code ArrayList} of {@code Index}
     * and returns it. Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if any index is invalid (not non-zero unsigned integer).
     */
    public static ArrayList<Index> parseMultipleIndexes(String oneBasedIndexes) throws ParseException {
        String trimmedIndex = oneBasedIndexes.trim();
        String[] indexList = trimmedIndex.split("\\s+");
        ArrayList<Index> output = new ArrayList<>();
        for (String index : indexList) {
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            output.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        return output;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String normalizedName = normalizeName(name);
        if (!Name.isValidName(normalizedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(normalizedName);
    }

    /**
     * Normalizes the given name by trimming, collapsing multiple spaces, and applying capitalization.
     */
    private static String normalizeName(String name) {
        String collapsed = name.trim().replaceAll("\\s+", " ");
        StringBuilder builder = new StringBuilder(collapsed.length());
        boolean capitalizeNext = true;
        for (int i = 0; i < collapsed.length(); i++) {
            char currentChar = collapsed.charAt(i);
            if (Character.isLetter(currentChar)) {
                builder.append(capitalizeNext
                        ? Character.toUpperCase(currentChar)
                        : Character.toLowerCase(currentChar));
                capitalizeNext = false;
            } else {
                builder.append(currentChar);
                capitalizeNext = currentChar == ' ' || currentChar == '-' || currentChar == '\'';
            }
        }
        return builder.toString();
    }

    /**
     * Parses a {@code String gamerTag} into a {@code GamerTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gamerTag} is invalid.
     */
    public static GamerTag parseGamerTag(String gamerTag) throws ParseException {
        requireNonNull(gamerTag);
        String trimmedGamerTag = gamerTag.trim();
        if (!GamerTag.isValidGamerTag(trimmedGamerTag)) {
            throw new ParseException(GamerTag.MESSAGE_CONSTRAINTS);
        }
        return new GamerTag(trimmedGamerTag);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroup(trimmedGroup)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }

    /**
     * Parses a {@code String server} into a {@code Server}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code server} is invalid.
     */
    public static Server parseServer(String server) throws ParseException {
        requireNonNull(server);
        String trimmedServer = server.trim();
        if (!Server.isValidServer(trimmedServer)) {
            throw new ParseException(Server.MESSAGE_CONSTRAINTS);
        }
        return new Server(trimmedServer);
    }

    /**
     * Parses a {@code String favourite} into a {@code Favourite}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code favourite} is invalid.
     */
    public static Favourite parseFavourite(String favourite) throws ParseException {
        requireNonNull(favourite);
        String trimmedFavourite = favourite.trim();
        if (!Favourite.isValidFavourite(trimmedFavourite)) {
            throw new ParseException(Favourite.MESSAGE_CONSTRAINTS);
        }
        return new Favourite(trimmedFavourite);
    }

    /**
     * Parses a {@code String country} into a {@code Country}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code country} is invalid.
     */
    public static Country parseCountry(String country) throws ParseException {
        requireNonNull(country);
        String trimmedCountry = country.trim();
        if (!Country.isValidCountry(trimmedCountry)) {
            throw new ParseException(Country.MESSAGE_CONSTRAINTS);
        }
        return new Country(trimmedCountry);
    }

    /**
     * Parses a {@code String region} into a {@code Region}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code region} is invalid.
     */
    public static Region parseRegion(String region) throws ParseException {
        requireNonNull(region);
        String trimmedRegion = region.trim();
        if (!Region.isValidRegion(trimmedRegion)) {
            throw new ParseException(Region.MESSAGE_CONSTRAINTS);
        }
        return new Region(trimmedRegion);
    }

    /**
     * Parses a {@code String note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

}

