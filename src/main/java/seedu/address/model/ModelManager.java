package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.gamer.Gamer;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BlockBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Gamer> filteredGamers;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyBlockBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new BlockBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredGamers = new FilteredList<>(this.addressBook.getGamerList());
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

    //=========== AddressBook ================================================================================

    @Override
    public void setBlockBook(ReadOnlyBlockBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBlockBook getBlockBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Gamer gamer) {
        requireNonNull(gamer);
        return addressBook.hasGamer(gamer);
    }

    @Override
    public void deletePerson(Gamer target) {
        addressBook.removeGamer(target);
    }

    @Override
    public void addGamer(Gamer gamer) {
        addressBook.addGamer(gamer);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Gamer target, Gamer editedGamer) {
        requireAllNonNull(target, editedGamer);

        addressBook.setGamer(target, editedGamer);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Gamer> getFilteredPersonList() {
        return filteredGamers;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Gamer> predicate) {
        requireNonNull(predicate);
        filteredGamers.setPredicate(predicate);
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
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredGamers.equals(otherModelManager.filteredGamers);
    }

}
