package seedu.blockbook.ui;

import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.blockbook.model.gamer.Group;

/**
 * Panel containing the list of groups displayed horizontally.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";

    @FXML
    private HBox groupContainer;
    @FXML
    private ScrollPane groupScrollPane;

    /**
     * Creates a {@code GroupListPanel} with the given {@code ObservableList}.
     */
    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        refreshGroupCards(groupList);
        groupList.addListener((ListChangeListener<Group>) change -> refreshGroupCards(groupList));
    }

    @FXML
    private void initialize() {
        groupScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        groupScrollPane.setPannable(false);
        groupScrollPane.setVvalue(0);
        groupScrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() != 0) {
                groupScrollPane.setVvalue(0);
            }
        });
        groupScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0 && !event.isShiftDown()) {
                event.consume();
            }
        });
    }

    private void refreshGroupCards(List<Group> groups) {
        groupContainer.getChildren().clear();
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            groupContainer.getChildren().add(new GroupCard(group, i + 1).getRoot());
        }
    }
}
