package seedu.blockbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.gamer.Gamer;

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
    private Label region;
    // @FXML
    // private Label email;
    // @FXML
    // private Label phone;
    // @FXML
    // private Label group;
    // @FXML
    // private Label country;
    // @FXML
    // private Label region;
    // @FXML
    // private Label note;

    /**
     * Creates a {@code GamerCard} with the given {@code Gamer} and index to display.
     */
    public GamerCard(Gamer gamer, int displayedIndex) {
        super(FXML);
        this.gamer = gamer;
        id.setText("#" + displayedIndex);
        gamerTag.setText("@" + gamer.getGamerTag().fullGamerTag);
        name.setText(Messages.formatNullable(gamer.getName()));
        server.setText(Messages.formatNullable(gamer.getServer()));
        region.setText(Messages.formatNullable(gamer.getRegion()));
        updateFavouriteIcon(gamer.getFavourite().toString());
        // email.setText("Email: " + Messages.formatNullable(gamer.getEmail()));
        // phone.setText("Phone: " + Messages.formatNullable(gamer.getPhone()));
        // group.setText("Group: " + Messages.formatNullable(gamer.getGroup()));
        // country.setText("Country: " + Messages.formatNullable(gamer.getCountry()));
        // note.setText("Note: " + Messages.formatNullable(gamer.getNote()));

        // region.setText(gamer.getRegion().fullRegion);
        // phone.setText(gamer.getPhone().value);
        // email.setText(gamer.getEmail().value);
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
    }
}
