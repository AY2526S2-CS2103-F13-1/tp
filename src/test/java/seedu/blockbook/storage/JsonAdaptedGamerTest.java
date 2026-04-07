package seedu.blockbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.blockbook.storage.JsonAdaptedGamer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.exceptions.IllegalValueException;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Phone;

public class JsonAdaptedGamerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GAMER_TAG = "R@chel";
    private static final String INVALID_PHONE = "abc";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SERVER = "server name";
    private static final String INVALID_FAVOURITE = "maybe";
    private static final String INVALID_COUNTRY = "SG123";
    private static final String INVALID_REGION = "Singapore";
    private static final String INVALID_NOTE = "note with spaces";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_GAMER_TAG = BENSON.getGamerTag().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_GROUP = BENSON.getGroups().get(0).toString();
    private static final List<Group> GROUP_LIST = List.of(new Group(VALID_GROUP));
    private static final List<Integer> VALID_GROUP_INDICES = List.of(0);
    private static final String VALID_SERVER = BENSON.getServer().toString();
    private static final boolean VALID_FAVOURITE = BENSON.getFavourite().isFav();
    private static final String VALID_COUNTRY = BENSON.getCountry().toString();
    private static final String VALID_REGION = BENSON.getRegion().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();

    @Test
    public void toModelType_validGamerDetails_returnsGamer() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(BENSON, GROUP_LIST);
        assertEquals(BENSON, gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(INVALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidGamerTag_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, INVALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, GamerTag.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, INVALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, INVALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Email.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    // @Test
    // public void toModelType_nullName_throwsIllegalValueException() throws IllegalValueException {
    //     JsonAdaptedGamer gamer = new JsonAdaptedGamer(null, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
    //             VALID_GROUP, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
    //     Gamer modelGamer = gamer.toModelType();
    //
    //     assertNull(modelGamer.getName());
    //     assertEquals(VALID_GAMER_TAG, modelGamer.getGamerTag().toString());
    // }

    @Test
    public void toModelType_nullGamerTag_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GamerTag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> gamer.toModelType(GROUP_LIST));
    }

    // @Test
    // public void toModelType_nullPhone_throwsIllegalValueException() {
    //     JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, null, VALID_EMAIL,
    //             VALID_GROUP, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
    //     String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
    //     assertThrows(IllegalValueException.class, expectedMessage, gamer::toModelType);
    // }
}
