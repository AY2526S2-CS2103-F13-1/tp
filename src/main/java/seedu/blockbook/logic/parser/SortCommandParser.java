package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GAMERTAG;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_SERVER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.commands.SortCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String MESSAGE_INVALID_ATTRIBUTES =
            "Invalid attributes detected: %1$s. Please provide only valid sort attributes.";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE =
            "Duplicate attribute detected: %1$s. Each attribute can only be specified once.";

    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(\\w+)/");
    private static final Set<String> VALID_SORT_ATTRIBUTES = Stream.of(
            PREFIX_GAMERTAG,
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL,
            PREFIX_GROUP,
            PREFIX_SERVER,
            PREFIX_FAVOURITE,
            PREFIX_COUNTRY,
            PREFIX_REGION,
            PREFIX_NOTE
    ).map(Prefix::getPrefix)
            .map(SortCommandParser::toAttributeName)
            .collect(Collectors.toUnmodifiableSet());

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        logger.fine("Parsing sort command arguments: " + args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new SortCommand(new ArrayList<>());
        }

        List<String> attributes = tokenizeArguments(trimmedArgs);
        validateArguments(trimmedArgs, attributes);
        verifyValidAttributes(attributes);
        verifyNoDuplicateAttributes(attributes);

        assert !attributes.isEmpty() : "Attributes list should not be empty after parsing";
        assert VALID_SORT_ATTRIBUTES.containsAll(attributes)
                : "All parsed attributes should be valid";

        logger.fine("Parsed sort attributes: " + attributes);
        return new SortCommand(attributes);
    }

    /**
     * Tokenizes raw sort arguments into attribute names.
     *
     * @param trimmedArgs Sort arguments with leading/trailing whitespace removed.
     * @return Ordered list of parsed attribute names.
     */
    private List<String> tokenizeArguments(String trimmedArgs) {
        List<String> attributes = new ArrayList<>();
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(trimmedArgs);
        while (matcher.find()) {
            attributes.add(matcher.group(1));
        }
        return attributes;
    }

    /**
     * Validates overall sort input format.
     *
     * @param trimmedArgs Sort arguments with leading/trailing whitespace removed.
     * @param attributes Parsed attribute names.
     * @throws ParseException If arguments are malformed.
     */
    private void validateArguments(String trimmedArgs, List<String> attributes) throws ParseException {
        String remaining = trimmedArgs.replaceAll("\\w+/", "").trim();
        if (!remaining.isEmpty() || attributes.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Ensures every provided attribute is supported by sort.
     *
     * @param attributes Parsed attribute names.
     * @throws ParseException If any attribute is invalid.
     */
    private void verifyValidAttributes(List<String> attributes) throws ParseException {
        Set<String> invalidAttributes = new LinkedHashSet<>();
        for (String attribute : attributes) {
            if (!VALID_SORT_ATTRIBUTES.contains(attribute)) {
                invalidAttributes.add(attribute);
            }
        }

        if (invalidAttributes.isEmpty()) {
            return;
        }

        if (invalidAttributes.size() == 1) {
            throw new ParseException(
                    String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, invalidAttributes.iterator().next()));
        }

        String invalidAttributeNames = String.join(", ", invalidAttributes);
        throw new ParseException(String.format(MESSAGE_INVALID_ATTRIBUTES, invalidAttributeNames));
    }

    private static String toAttributeName(String prefix) {
        return prefix.substring(0, prefix.length() - 1);
    }

    /**
     * Ensures no sort attribute is repeated.
     *
     * @param attributes Parsed attribute names.
     * @throws ParseException If duplicate attributes are found.
     */
    private void verifyNoDuplicateAttributes(List<String> attributes) throws ParseException {
        Set<String> seenAttributes = new HashSet<>();
        Set<String> duplicateAttributes = new LinkedHashSet<>();

        for (String attribute : attributes) {
            if (!seenAttributes.add(attribute)) {
                duplicateAttributes.add(attribute);
            }
        }

        if (!duplicateAttributes.isEmpty()) {
            String duplicateNames = String.join(", ", duplicateAttributes);
            throw new ParseException(
                    String.format(MESSAGE_DUPLICATE_ATTRIBUTE, duplicateNames));
        }
    }
}
