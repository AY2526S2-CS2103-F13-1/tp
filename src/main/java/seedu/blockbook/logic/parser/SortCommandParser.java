package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.commands.SortCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(\\w+)/");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        logger.fine("Parsing sort command arguments: " + args);
        String trimmedArgs = args.trim();

        // No arguments means default sort by gamertag
        if (trimmedArgs.isEmpty()) {
            return new SortCommand(new ArrayList<>());
        }

        // Validate that the input only contains valid attribute/ patterns
        // Remove all attribute/ patterns and check if anything remains
        String remaining = trimmedArgs.replaceAll("\\w+/", "").trim();
        if (!remaining.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Extract attribute names from the input
        List<String> attributes = new ArrayList<>();
        Set<String> seenAttributes = new HashSet<>();
        Set<String> duplicateAttributes = new LinkedHashSet<>();
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(trimmedArgs);
        while (matcher.find()) {
            String attribute = matcher.group(1).toLowerCase();
            if (!SortCommand.VALID_ATTRIBUTES.contains(attribute)) {
                throw new ParseException(
                        String.format(SortCommand.MESSAGE_INVALID_ATTRIBUTE, attribute));
            }
            if (!seenAttributes.add(attribute)) {
                duplicateAttributes.add(attribute);
                continue;
            }
            attributes.add(attribute);
        }

        if (!duplicateAttributes.isEmpty()) {
            String duplicateNames = duplicateAttributes.stream()
                    .collect(Collectors.joining(", "));
            throw new ParseException(
                    String.format(SortCommand.MESSAGE_DUPLICATE_ATTRIBUTE, duplicateNames));
        }

        if (attributes.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        assert !attributes.isEmpty() : "Attributes list should not be empty after parsing";
        assert attributes.stream().allMatch(SortCommand.VALID_ATTRIBUTES::contains)
                : "All parsed attributes should be valid";

        logger.fine("Parsed sort attributes: " + attributes);
        return new SortCommand(attributes);
    }
}
