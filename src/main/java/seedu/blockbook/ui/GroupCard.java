package seedu.blockbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.blockbook.model.gamer.Group;

/**
 * An UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupCard.fxml";

    public final Group group;

    @FXML
    private HBox groupCard;
    @FXML
    private Label groupIndex;
    @FXML
    private Label groupName;

    /**
     * Creates a {@code GroupCard} with the given {@code Group} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        groupIndex.setText(displayedIndex + ".");
        groupName.setText(group.toString());
    }
}
