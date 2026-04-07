package seedu.blockbook.model;
import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.UniqueGamerList;
import seedu.blockbook.model.gamer.UniqueGroupList;

/**
 * Wraps all data at the BlockBook level.
 * Duplicates are not allowed (by .isSameGamer comparison).
 */
public class BlockBook implements ReadOnlyBlockBook {

    private final UniqueGamerList gamers;
    private final UniqueGroupList groups;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        gamers = new UniqueGamerList();
        groups = new UniqueGroupList();
    }

    public BlockBook() {}

    /**
     * Creates a BlockBook using the gamers in {@code toBeCopied}.
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
     * Resets the existing data of this {@code BlockBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBlockBook newData) {
        requireNonNull(newData);

        setGroups(newData.getGroupList());
        setGamers(newData.getGamerList());
    }

    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    //// gamer-level operations

    /**
     * Returns true if a gamer with the same identity as {@code gamer} exists in the BlockBook.
     */
    public boolean hasGamer(Gamer gamer) {
        requireNonNull(gamer);
        return gamers.contains(gamer);
    }

    /**
     * Returns true if a group with the same name as {@code group} exists in the BlockBook (case-insensitive).
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * Adds a gamer to the BlockBook.
     * The gamer must not already exist in the BlockBook.
     */
    public void addGamer(Gamer p) {
        gamers.add(p);
    }

    /**
     * Adds a group to the BlockBook.
     * The group must not already exist in the BlockBook.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Removes a group from the BlockBook.
     * The group must exist in the BlockBook.
     */
    public void removeGroup(Group group) {
        groups.remove(group);
    }

    /**
     * Replaces the given gamer {@code target} in the list with {@code editedGamer}.
     * {@code target} must exist in the BlockBook.
     * The gamer identity of {@code editedGamer} must not be the same as another existing gamer in the BlockBook.
     */
    public void setGamer(Gamer target, Gamer editedGamer) {
        requireNonNull(editedGamer);

        gamers.setGamer(target, editedGamer);
    }

    /**
     * Removes {@code key} from this {@code BlockBook}.
     * {@code key} must exist in the BlockBook.
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

    /**
     * Returns an unmodifiable view of the group list.
     */
    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
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

        BlockBook otherBlockBook = (BlockBook) other;
        return gamers.equals(otherBlockBook.gamers)
                && groups.equals(otherBlockBook.groups);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(gamers, groups);
    }
}
