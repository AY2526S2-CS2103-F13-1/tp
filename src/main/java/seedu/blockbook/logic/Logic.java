package seedu.blockbook.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.logic.commands.CommandResult;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.gamer.Gamer;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the BlockBook.
     *
     * @see seedu.blockbook.model.Model#getBlockBook()
     */
    ReadOnlyBlockBook getBlockBook();

    /** Returns an unmodifiable view of the filtered list of gamers */
    ObservableList<Gamer> getFilteredGamerList();

    /**
     * Returns the user prefs' BlockBook file path.
     */
    Path getBlockBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}


