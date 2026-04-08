package seedu.blockbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.blockbook.storage.JsonAdaptedGamer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.exceptions.IllegalValueException;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;

public class JsonAdaptedGamerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GAMER_TAG = "R@chel";
    private static final String INVALID_PHONE = "abc";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SERVER = "server name";
    private static final String INVALID_COUNTRY = "SG123";
    private static final String INVALID_REGION = "Singapore";
    private static final String INVALID_NOTE = "note$";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_GAMER_TAG = BENSON.getGamerTag().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_GROUP = BENSON.getGroup().toString();
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
    public void toModelType_nullOptionalFields_returnsGamerWithNullOptionalFields() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                null, VALID_GAMER_TAG, null, null,
                List.of(), null, null, null, null, null);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new GamerTag(VALID_GAMER_TAG), modelGamer.getGamerTag());
        assertEquals(null, modelGamer.getName());
        assertEquals(null, modelGamer.getPhone());
        assertEquals(null, modelGamer.getEmail());
        assertEquals(null, modelGamer.getServer());
        assertEquals(null, modelGamer.getCountry());
        assertEquals(null, modelGamer.getRegion());
        assertEquals(null, modelGamer.getNote());
        assertEquals(List.of(), modelGamer.getGroups());
        assertFalse(modelGamer.getFavourite().isFav());
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
    public void toModelType_nullGamerTag_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GamerTag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> gamer.toModelType(GROUP_LIST));
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

    @Test
    public void toModelType_invalidServer_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, INVALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Server.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidCountry_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, INVALID_COUNTRY, VALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Country.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidRegion_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, INVALID_REGION, VALID_NOTE);
        assertThrows(IllegalValueException.class, Region.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, INVALID_NOTE);
        assertThrows(IllegalValueException.class, Note.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_nullFavourite_defaultsToFalse() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, null, VALID_COUNTRY, VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);
        assertFalse(modelGamer.getFavourite().isFav());
        assertEquals(new Favourite(false), modelGamer.getFavourite());
    }

    @Test
    public void toModelType_normalizesNamePhoneAndCountry() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                "bEnSoN   meier", VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, "mAlAySiA", VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Name("Benson Meier"), modelGamer.getName());
        assertEquals(new Country("Malaysia"), modelGamer.getCountry());
    }

    @Test
    public void toModelType_phoneWithExtraWhitespace_throwsIllegalValueException() {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                VALID_NAME, VALID_GAMER_TAG, "  +65   22222222  ", VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);

        assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS, () -> gamer.toModelType(GROUP_LIST));
    }

    @Test
    public void toModelType_nameWithSpacesAndLowercase_normalizesName() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                "hero   brine", VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Name("Hero Brine"), modelGamer.getName());
    }

    @Test
    public void toModelType_countryWithSpacesAndLowercase_normalizesCountry() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, "united   states", VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Country("United States"), modelGamer.getCountry());
    }

    @Test
    public void toModelType_nameWithSpacesHyphenAndApostrophe_normalizesName() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                "jean-luc    o'neill", VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Name("Jean-Luc O'Neill"), modelGamer.getName());
    }

    @Test
    public void toModelType_countryWithHyphen_normalizesCountry() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, "timor-leste", VALID_REGION, VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Country("Timor-Leste"), modelGamer.getCountry());
    }

    @Test
    public void toModelType_regionWithLowercase_returnsUppercaseRegion() throws Exception {
        JsonAdaptedGamer gamer = new JsonAdaptedGamer(
                VALID_NAME, VALID_GAMER_TAG, VALID_PHONE, VALID_EMAIL,
                VALID_GROUP_INDICES, VALID_SERVER, VALID_FAVOURITE, VALID_COUNTRY, "asia", VALID_NOTE);

        Gamer modelGamer = gamer.toModelType(GROUP_LIST);

        assertEquals(new Region("ASIA"), modelGamer.getRegion());
    }
}
