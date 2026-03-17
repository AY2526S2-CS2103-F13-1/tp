package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /**
     * Shows a message in the FXML resultDisplay element
     * @param message Intended message to be shown
     */
    void showMessage(String message);
}
