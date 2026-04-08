package seedu.blockbook.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * UI component that displays information of a {@code Gamer} for the view popup.
 */
public class GamerPopupCard extends UiPart<Region> {

    private static final String FXML = "GamerPopupCard.fxml";
    private static final Image FAVOURITE_ICON = new Image(
            GamerPopupCard.class.getResourceAsStream("/images/nether_star.gif"));

    public final Gamer gamer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label gamerTag;
    @FXML
    private Label id;
    @FXML
    private Label server;
    @FXML
    private ImageView favouriteIcon;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label favourite;
    @FXML
    private Label country;
    @FXML
    private Label region;
    @FXML
    private TextArea note;
    @FXML
    private Button copyButton;
    @FXML
    private VBox groupList;
    @FXML
    private ScrollPane groupScrollPane;

    /**
     * Creates a {@code GamerPopupCard} with the given {@code Gamer} and index to display.
     */
    public GamerPopupCard(Gamer gamer, int displayedIndex) {
        super(FXML);
        this.gamer = gamer;
        id.setText("#" + displayedIndex);
        gamerTag.setText("@" + gamer.getGamerTag().fullGamerTag);
        name.setText(Messages.formatNullable(gamer.getName()));
        phone.setText(Messages.formatNullable(gamer.getPhone()));
        email.setText(Messages.formatNullable(gamer.getEmail()));
        server.setText(Messages.formatNullable(gamer.getServer()));
        favourite.setText(Messages.formatNullable(gamer.getFavourite()));
        country.setText(Messages.formatNullable(gamer.getCountry()));
        region.setText(Messages.formatNullable(gamer.getRegion()));
        note.setText(Messages.formatNullable(gamer.getNote()));

        updateFavouriteIcon(gamer.getFavourite() == null ? "N/A" : gamer.getFavourite().toString());
        populateGroupList(gamer);
        groupScrollPane.setFitToWidth(true);
    }

    private void populateGroupList(Gamer gamer) {
        groupList.getChildren().clear();
        if (gamer.getGroups().isEmpty()) {
            Label placeholder = new Label("N/A");
            placeholder.getStyleClass().add("popup-group-item");
            placeholder.setWrapText(true);
            groupList.getChildren().add(placeholder);
            return;
        }
        for (int i = 0; i < gamer.getGroups().size(); i++) {
            Group group = gamer.getGroups().get(i);
            Label groupLabel = new Label((i + 1) + ". " + group);
            groupLabel.getStyleClass().add("popup-group-item");
            groupLabel.setWrapText(true);
            groupLabel.setMaxWidth(Double.MAX_VALUE);
            groupList.getChildren().add(groupLabel);
        }
    }

    /**
     * Applies the visual state for the favourite badge.
     */
    private void updateFavouriteIcon(String favouriteState) {
        boolean isFavourite = "Yes".equalsIgnoreCase(favouriteState);
        favouriteIcon.setImage(isFavourite ? FAVOURITE_ICON : null);
        favouriteIcon.setVisible(isFavourite);
        favouriteIcon.setManaged(isFavourite);
    }

    /**
     * Copies the gamer's details to the clipboard.
     */
    @FXML
    private void copyDetails() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent details = new ClipboardContent();
        details.putString(Messages.format(gamer));
        clipboard.setContent(details);

        String originalText = copyButton.getText();
        copyButton.setText("Copied!");
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> copyButton.setText(originalText));
        pause.play();
    }
}
