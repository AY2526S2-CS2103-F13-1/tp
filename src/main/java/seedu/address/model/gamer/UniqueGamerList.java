package seedu.address.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.gamer.exceptions.DuplicateGamerException;
import seedu.address.model.gamer.exceptions.GamerNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Gamer#isSameGamer(Gamer)
 */
public class UniqueGamerList implements Iterable<Gamer> {

    private final ObservableList<Gamer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Gamer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent gamer as the given argument.
     */
    public boolean contains(Gamer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGamer);
    }

    /**
     * Adds a gamer to the list.
     * The gamer must not already exist in the list.
     */
    public void add(Gamer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGamerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the gamer {@code target} in the list with {@code editedGamer}.
     * {@code target} must exist in the list.
     * The gamer identity of {@code editedGamer} must not be the same as another existing gamer in the list.
     */
    public void setGamer(Gamer target, Gamer editedGamer) {
        requireAllNonNull(target, editedGamer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GamerNotFoundException();
        }

        if (!target.isSameGamer(editedGamer) && contains(editedGamer)) {
            throw new DuplicateGamerException();
        }

        internalList.set(index, editedGamer);
    }

    /**
     * Removes the equivalent gamer from the list.
     * The gamer must exist in the list.
     */
    public void remove(Gamer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GamerNotFoundException();
        }
    }

    public void setGamers(UniqueGamerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code gamers}.
     * {@code gamers} must not contain duplicate gamers.
     */
    public void setGamers(List<Gamer> gamers) {
        requireAllNonNull(gamers);
        if (!personsAreUnique(gamers)) {
            throw new DuplicateGamerException();
        }

        internalList.setAll(gamers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Gamer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Gamer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueGamerList)) {
            return false;
        }

        UniqueGamerList otherUniqueGamerList = (UniqueGamerList) other;
        return internalList.equals(otherUniqueGamerList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code gamers} contains only unique gamers.
     */
    private boolean personsAreUnique(List<Gamer> gamers) {
        for (int i = 0; i < gamers.size() - 1; i++) {
            for (int j = i + 1; j < gamers.size(); j++) {
                if (gamers.get(i).isSameGamer(gamers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
