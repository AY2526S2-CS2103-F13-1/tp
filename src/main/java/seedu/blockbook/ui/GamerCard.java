package seedu.blockbook.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * An UI component that displays information of a {@code Gamer}.
 */
public class GamerCard extends UiPart<Region> {

    private static final String FXML = "GamerListCard.fxml";
    private static final Image FAVOURITE_ICON = new Image(
            GamerCard.class.getResourceAsStream("/images/nether_star.gif"));

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private Label group;
    @FXML
    private Label favourite;
    @FXML
    private Label country;
    @FXML
    private Label region;
    @FXML
    private Label note;
    @FXML
    private Button copyButton;

    /**
     * Creates a {@code GamerCard} with the given {@code Gamer} and index to display.
     */
    public GamerCard(Gamer gamer, int displayedIndex) {
        super(FXML);
        this.gamer = gamer;
        id.setText("#" + displayedIndex);
        gamerTag.setText("@" + gamer.getGamerTag().fullGamerTag);
        name.setText(Messages.formatNullable(gamer.getName()));
        phone.setText(Messages.formatNullable(gamer.getPhone()));
        email.setText(Messages.formatNullable(gamer.getEmail()));
        group.setText(formatGroups(gamer.getGroups()));
        server.setText(Messages.formatNullable(gamer.getServer()));
        favourite.setText(Messages.formatNullable(gamer.getFavourite()));
        country.setText(Messages.formatNullable(gamer.getCountry()));
        region.setText(Messages.formatNullable(gamer.getRegion()));
        note.setText(Messages.formatNullable(gamer.getNote()));
        updateFavouriteIcon(gamer.getFavourite().toString());
    }

    private static String formatGroups(List<Group> groups) {
        if (groups == null || groups.isEmpty()) {
            return "N/A";
        }
        return groups.stream()
                .map(Group::toString)
                .collect(Collectors.joining(", "));
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

        // Change button text to indicate copy success
        String originalText = copyButton.getText();
        copyButton.setText("Copied!");

        // Revert button text after a short delay
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> copyButton.setText(originalText));
        pause.play();
    }
}
