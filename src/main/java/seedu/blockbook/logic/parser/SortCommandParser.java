package seedu.blockbook.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.blockbook.logic.commands.SortCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(\\w+)/");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // No arguments means default sort by gamertag
        if (trimmedArgs.isEmpty()) {
            return new SortCommand(new ArrayList<>());
        }

        // Validate that the input only contains valid attribute/ patterns
        // Remove all attribute/ patterns and check if anything remains
        String remaining = trimmedArgs.replaceAll("\\w+/", "").trim();
        if (!remaining.isEmpty()) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ATTRIBUTES);
        }

        // Extract attribute names from the input
        List<String> attributes = new ArrayList<>();
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(trimmedArgs);
        while (matcher.find()) {
            String attribute = matcher.group(1).toLowerCase();
            if (!SortCommand.VALID_ATTRIBUTES.contains(attribute)) {
                throw new ParseException(SortCommand.MESSAGE_INVALID_ATTRIBUTES);
            }
            attributes.add(attribute);
        }

        if (attributes.isEmpty()) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ATTRIBUTES);
        }

        assert !attributes.isEmpty() : "Attributes list should not be empty after parsing";
        assert attributes.stream().allMatch(SortCommand.VALID_ATTRIBUTES::contains)
                : "All parsed attributes should be valid";

        return new SortCommand(attributes);
    }
}
