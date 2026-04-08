package seedu.blockbook.logic.parser;

import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.commands.ClearCommand;
import seedu.blockbook.logic.commands.Command;
import seedu.blockbook.logic.commands.DeleteCommand;
import seedu.blockbook.logic.commands.EditCommand;
import seedu.blockbook.logic.commands.ExitCommand;
import seedu.blockbook.logic.commands.FavouriteCommand;
import seedu.blockbook.logic.commands.FindCommand;
import seedu.blockbook.logic.commands.GroupAddCommand;
import seedu.blockbook.logic.commands.GroupCreateCommand;
import seedu.blockbook.logic.commands.GroupEditCommand;
import seedu.blockbook.logic.commands.GroupNukeCommand;
import seedu.blockbook.logic.commands.GroupRemoveCommand;
import seedu.blockbook.logic.commands.GroupViewCommand;
import seedu.blockbook.logic.commands.HelpCommand;
import seedu.blockbook.logic.commands.ListCommand;
import seedu.blockbook.logic.commands.SortCommand;
import seedu.blockbook.logic.commands.ViewCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class BlockBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(BlockBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case FavouriteCommand.COMMAND_WORD:
        case FavouriteCommand.COMMAND_ALIAS:
            return new FavouriteCommandParser(true).parse(arguments);

        case FavouriteCommand.COMMAND_WORD_UNFAVOURITE:
        case FavouriteCommand.COMMAND_ALIAS_UNFAVOURITE:
            return new FavouriteCommandParser(false).parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_ALIAS:
            return new ViewCommandParser().parse(arguments);

        case GroupCreateCommand.COMMAND_WORD:
            return new GroupCommandParser().parse(arguments);

        case GroupEditCommand.COMMAND_WORD:
        case GroupEditCommand.COMMAND_ALIAS:
            return new GroupEditCommandParser().parse(arguments);

        case GroupAddCommand.COMMAND_WORD:
        case GroupAddCommand.COMMAND_ALIAS:
            return new GroupAddCommandParser().parse(arguments);

        case GroupRemoveCommand.COMMAND_WORD:
        case GroupRemoveCommand.COMMAND_ALIAS:
            return new GroupRemoveCommandParser().parse(arguments);
            
        case GroupViewCommand.COMMAND_WORD:
        case GroupViewCommand.COMMAND_ALIAS:
            return new GroupViewCommandParser().parse(arguments);
           
        case GroupNukeCommand.COMMAND_WORD:
        case GroupNukeCommand.COMMAND_ALIAS:
            return new GroupNukeCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
