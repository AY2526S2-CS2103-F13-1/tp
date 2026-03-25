package seedu.blockbook.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.model.gamer.Gamer;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Gamer> PREDICATE_SHOW_ALL_GAMERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Contact file path.
     */
    Path getBlockBookFilePath();

    /**
     * Sets the user prefs' BlockBook file path.
     */
    void setBlockBookFilePath(Path blockBookFilePath);


    /**
     * Replaces BlockBook data with the data in {@code blockBook}.
     */
    void setBlockBook(ReadOnlyBlockBook blockBook);

    /** Returns the BlockBook */
    ReadOnlyBlockBook getBlockBook();

    /**
     * Returns true if a gamer with the same identity as {@code gamer} exists in the BlockBook.
     */
    boolean hasGamer(Gamer gamer);

    /**
     * Deletes the given gamer.
     * The gamer must exist in the BlockBook.
     */
    void deleteGamer(Gamer target);

    /**
     * Adds the given gamer.
     * {@code gamer} must not already exist in the BlockBook.
     */
    void addGamer(Gamer gamer);

    /**
     * Replaces the given gamer {@code target} with {@code editedGamer}.
     * {@code target} must exist in the BlockBook.
     * The gamer identity of {@code editedGamer} must not be the same as another existing gamer in the BlockBook.
     */
    void setGamer(Gamer target, Gamer editedGamer);

    /** Returns an unmodifiable view of the filtered gamer list */
    ObservableList<Gamer> getFilteredGamerList();

    /**
     * Updates the filter of the filtered gamer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGamerList(Predicate<Gamer> predicate);

    /**
     * Sorts the gamer list using the given {@code comparator}.
     * This sort is session-based and does not persist to storage.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortGamerList(Comparator<Gamer> comparator);
}


