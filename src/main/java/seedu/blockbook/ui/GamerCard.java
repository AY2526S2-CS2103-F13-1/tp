package seedu.blockbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.gamer.Gamer;

/**
 * An UI component that displays information of a {@code Gamer}.
 */
public class GamerCard extends UiPart<Region> {

    private static final String FXML = "GamerListCard.fxml";

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
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label group;
    @FXML
    private Label server;
    @FXML
    private Label favourite;
    @FXML
    private Label country;
    @FXML
    private Label region;
    @FXML
    private Label note;

    /**
     * Creates a {@code GamerCard} with the given {@code Gamer} and index to display.
     */
    public GamerCard(Gamer gamer, int displayedIndex) {
        super(FXML);
        this.gamer = gamer;
        id.setText(displayedIndex + ". ");
        gamerTag.setText("GamerTag: " + gamer.getGamerTag().fullGamerTag);
        name.setText("Name: " + Messages.formatNullable(gamer.getName()));
        phone.setText("Phone: " + Messages.formatNullable(gamer.getPhone()));
        email.setText("Email: " + Messages.formatNullable(gamer.getEmail()));
        group.setText("Group: " + Messages.formatNullable(gamer.getGroup()));
        server.setText("Server: " + Messages.formatNullable(gamer.getServer()));
        country.setText("Country: " + Messages.formatNullable(gamer.getCountry()));
        region.setText("Region: " + Messages.formatNullable(gamer.getRegion()));
        note.setText("Note: " + Messages.formatNullable(gamer.getNote()));
        favourite.setText("Favourite: " + gamer.getFavourite().toString());

        // region.setText(gamer.getRegion().fullRegion);
        // phone.setText(gamer.getPhone().value);
        // email.setText(gamer.getEmail().value);
    }
}


