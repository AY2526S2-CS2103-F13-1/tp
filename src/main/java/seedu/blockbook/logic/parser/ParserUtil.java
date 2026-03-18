package seedu.blockbook.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.StringUtil;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Phone;

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

}

