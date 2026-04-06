package seedu.blockbook.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Controller for a view popup that shows a gamer's details.
 */
public class ViewWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ViewWindow.class);
    private static final String FXML = "ViewWindow.fxml";

    @FXML
    private StackPane cardPlaceholder;

    @FXML
    private ScrollPane cardScrollPane;

    /**
     * Creates a new ViewWindow.
     *
     * @param root Stage to use as the root of the ViewWindow.
     */
    public ViewWindow(Stage root) {
        super(FXML, root);
        configureWindow();
    }

    /**
     * Creates a new ViewWindow.
     */
    public ViewWindow() {
        this(new Stage());
    }

    private void configureWindow() {
        getRoot().setResizable(false);
        cardScrollPane.setFitToWidth(true);
        cardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * Shows the view window.
     */
    public void show() {
        logger.fine("Showing view window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the view window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the view window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the view window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Updates the popup to show the specified gamer.
     */
    public void setGamer(Gamer gamer, int displayedIndex) {
        GamerCard card = new GamerCard(gamer, displayedIndex);
        Region cardRoot = card.getRoot();
        StackPane.setAlignment(cardRoot, Pos.TOP_LEFT);
        cardPlaceholder.getChildren().setAll(cardRoot);
        if (gamer.getGamerTag() != null) {
            getRoot().setTitle("Gamer Card: @" + gamer.getGamerTag().fullGamerTag);
        } else {
            getRoot().setTitle("Gamer Card");
        }
    }
}
