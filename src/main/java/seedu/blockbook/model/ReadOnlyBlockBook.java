package seedu.blockbook.model;

import javafx.collections.ObservableList;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * Unmodifiable view of a BlockBook.
 */
public interface ReadOnlyBlockBook {

    /**
     * Returns an unmodifiable view of the gamer list.
     * This list will not contain any duplicate gamers.
     */
    ObservableList<Gamer> getGamerList();

    /**
     * Returns an unmodifiable view of the group list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<Group> getGroupList();

}
