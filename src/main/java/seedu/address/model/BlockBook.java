package seedu.address.model;
import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.gamer.Gamer;
import seedu.address.model.gamer.UniqueGamerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGamer comparison)
 */
public class BlockBook implements ReadOnlyBlockBook {

    private final UniqueGamerList gamers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        gamers = new UniqueGamerList();
    }

    public BlockBook() {}

    /**
     * Creates an AddressBook using the Gamers in the {@code toBeCopied}
     */
    public BlockBook(ReadOnlyBlockBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the gamer list with {@code gamers}.
     * {@code gamers} must not contain duplicate gamers.
     */
    public void setGamers(List<Gamer> gamers) {
        this.gamers.setGamers(gamers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBlockBook newData) {
        requireNonNull(newData);

        setGamers(newData.getGamerList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasGamer(Gamer gamer) {
        requireNonNull(gamer);
        return gamers.contains(gamer);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addGamer(Gamer p) {
        gamers.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedGamer}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedGamer} must not be the same as another existing person in the address book.
     */
    public void setGamer(Gamer target, Gamer editedGamer) {
        requireNonNull(editedGamer);

        gamers.setGamer(target, editedGamer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGamer(Gamer key) {
        gamers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("gamers", gamers)
                .toString();
    }

    @Override
    public ObservableList<Gamer> getGamerList() {
        return gamers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BlockBook)) {
            return false;
        }

        BlockBook otherAddressBook = (BlockBook) other;
        return gamers.equals(otherAddressBook.gamers);
    }

    @Override
    public int hashCode() {
        return gamers.hashCode();
    }
}
