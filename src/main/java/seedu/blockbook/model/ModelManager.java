package seedu.blockbook.model;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Represents the in-memory model of the BlockBook data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BlockBook blockBook;
    private final UserPrefs userPrefs;
    private final SortedList<Gamer> sortedGamers;
    private final FilteredList<Gamer> filteredGamers;

    /**
     * Initializes a ModelManager with the given block book data and user prefs.
     */
    public ModelManager(ReadOnlyBlockBook blockBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(blockBook, userPrefs);

        logger.fine("Initializing with BlockBook: " + blockBook + " and user prefs " + userPrefs);

        this.blockBook = new BlockBook(blockBook);
        this.userPrefs = new UserPrefs(userPrefs);
        sortedGamers = new SortedList<>(this.blockBook.getGamerList());
        filteredGamers = new FilteredList<>(sortedGamers);
    }

    public ModelManager() {
        this(new BlockBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getBlockBookFilePath() {
        return userPrefs.getBlockBookFilePath();
    }

    @Override
    public void setBlockBookFilePath(Path blockBookFilePath) {
        requireNonNull(blockBookFilePath);
        userPrefs.setBlockBookFilePath(blockBookFilePath);
    }

    //=========== BlockBook ================================================================================

    @Override
    public void setBlockBook(ReadOnlyBlockBook blockBook) {
        this.blockBook.resetData(blockBook);
    }

    @Override
    public ReadOnlyBlockBook getBlockBook() {
        return blockBook;
    }

    @Override
    public boolean hasGamer(Gamer gamer) {
        requireNonNull(gamer);
        return blockBook.hasGamer(gamer);
    }

    @Override
    public void deleteGamer(Gamer target) {
        blockBook.removeGamer(target);
    }

    @Override
    public void addGamer(Gamer gamer) {
        blockBook.addGamer(gamer);
        updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
    }

    @Override
    public void setGamer(Gamer target, Gamer editedGamer) {
        requireAllNonNull(target, editedGamer);

        blockBook.setGamer(target, editedGamer);
    }

    //=========== Filtered Gamer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Gamer} backed by the internal block book list.
     */
    @Override
    public ObservableList<Gamer> getFilteredGamerList() {
        return filteredGamers;
    }

    @Override
    public void updateFilteredGamerList(Predicate<Gamer> predicate) {
        requireNonNull(predicate);
        filteredGamers.setPredicate(predicate);
    }

    @Override
    public void sortGamerList(Comparator<Gamer> comparator) {
        requireNonNull(comparator);
        assert sortedGamers != null : "SortedList should have been initialized";
        sortedGamers.setComparator(comparator);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return blockBook.equals(otherModelManager.blockBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredGamers.equals(otherModelManager.filteredGamers);
    }

}


