package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.gamer.Gamer;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label region;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Gamer gamer, int displayedIndex) {
        super(FXML);
        this.gamer = gamer;
        id.setText(displayedIndex + ". ");
        name.setText(gamer.getName().fullName);
        gamerTag.setText("Gamertag: " + gamer.getGamerTag().fullGamerTag);
        // region.setText(person.getRegion().fullRegion);
        // phone.setText(person.getPhone().value);
        // email.setText(person.getEmail().value);
    }
}
