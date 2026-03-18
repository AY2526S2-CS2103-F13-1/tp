package seedu.blockbook.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Panel containing the list of gamers.
 */
public class GamerListPanel extends UiPart<Region> {
    private static final String FXML = "GamerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GamerListPanel.class);

    @FXML
    private ListView<Gamer> gamerListView;

    /**
     * Creates a {@code GamerListPanel} with the given {@code ObservableList}.
     */
    public GamerListPanel(ObservableList<Gamer> gamerList) {
        super(FXML);
        gamerListView.setItems(gamerList);
        gamerListView.setCellFactory(listView -> new GamerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Gamer} using a {@code GamerCard}.
     */
    class GamerListViewCell extends ListCell<Gamer> {
        @Override
        protected void updateItem(Gamer gamer, boolean empty) {
            super.updateItem(gamer, empty);

            if (empty || gamer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GamerCard(gamer, getIndex() + 1).getRoot());
            }
        }
    }

}


