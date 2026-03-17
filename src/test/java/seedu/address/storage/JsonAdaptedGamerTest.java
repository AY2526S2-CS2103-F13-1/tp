package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedGamer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGamers.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.gamer.GamerTag;
import seedu.address.model.gamer.Name;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;

//import seedu.address.model.gamer.Address;
//import seedu.address.model.gamer.Email;
//import seedu.address.model.gamer.Phone;

public class JsonAdaptedGamerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GAMER_TAG = "R@chel";

    // private static final String INVALID_PHONE = "+651234";
    // private static final String INVALID_ADDRESS = " ";
    // private static final String INVALID_EMAIL = "example.com";
    // private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_GAMER_TAG = BENSON.getGamerTag().toString();

    // private static final String VALID_PHONE = BENSON.getPhone().toString();
    // private static final String VALID_EMAIL = BENSON.getEmail().toString();


    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(BENSON);
        assertEquals(BENSON, gamer.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGamer gamer =
                new JsonAdaptedGamer(INVALID_NAME, VALID_GAMER_TAG);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    }

    @Test
    public void toModelType_invalidGamerTag_throwsIllegalValueException() {
        JsonAdaptedGamer gamer =
                new JsonAdaptedGamer(VALID_NAME, INVALID_GAMER_TAG);
        String expectedMessage = GamerTag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(null, VALID_GAMER_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    }

    @Test
    public void toModelType_nullGamerTag_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GamerTag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    }

    // @Test
    // public void toModelType_invalidPhone_throwsIllegalValueException() {
    // JsonAdaptedGamer gamer =
    // new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG);
    // String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
    // assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    // }

    // @Test
    // public void toModelType_nullPhone_throwsIllegalValueException() {
    // JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG);
    // String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
    // assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    // }

    // @Test
    // public void toModelType_invalidEmail_throwsIllegalValueException() {
    // JsonAdaptedGamer gamer =
    // new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG);
    // String expectedMessage = Email.MESSAGE_CONSTRAINTS;
    // assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    // }

    // @Test
    // public void toModelType_nullEmail_throwsIllegalValueException() {
    // JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG);
    // String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
    // assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    // }


}
