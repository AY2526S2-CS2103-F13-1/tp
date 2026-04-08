package seedu.blockbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GAMERTAG = "bad tag";
    private static final String INVALID_PHONE = "12";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_GROUP = "Raid Team 1";
    private static final String INVALID_SERVER = "server_name";
    private static final String INVALID_COUNTRY = "Singapore1";
    private static final String INVALID_REGION = "antarctica";
    private static final String INVALID_NOTE = "ranked,ready";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_GAMERTAG = "Herobrine_123";
    private static final String VALID_PHONE = "+65 9312-1534";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_GROUP = "Raid Team";
    private static final String VALID_SERVER = "127.0.0.1:8080";
    private static final String VALID_COUNTRY = "Singapore";
    private static final String VALID_REGION = "ASIA";
    private static final String VALID_NOTE = "ranked_ready";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1L)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_GAMER, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_GAMER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseMultipleIndexes_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("1 two 3"));
    }

    @Test
    public void parseMultipleIndexes_validInput_success() throws Exception {
        ArrayList<Index> expected = new ArrayList<>();
        expected.add(Index.fromOneBased(1));
        expected.add(Index.fromOneBased(2));
        expected.add(Index.fromOneBased(3));

        assertEquals(expected, ParserUtil.parseMultipleIndexes("1 2 3"));
        assertEquals(expected, ParserUtil.parseMultipleIndexes("  1   2   3  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsNormalizedName() throws Exception {
        String input = WHITESPACE + "rAcHel   waLKer" + WHITESPACE;
        Name expectedName = new Name("Rachel Walker");
        assertEquals(expectedName, ParserUtil.parseName(input));
    }

    @Test
    public void parseGamerTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGamerTag((String) null));
    }

    @Test
    public void parseGamerTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGamerTag(INVALID_GAMERTAG));
    }

    @Test
    public void parseGamerTag_validValueWithoutWhitespace_returnsGamerTag() throws Exception {
        GamerTag expectedGamerTag = new GamerTag(VALID_GAMERTAG);
        assertEquals(expectedGamerTag, ParserUtil.parseGamerTag(VALID_GAMERTAG));
    }

    @Test
    public void parseGamerTag_validValueWithWhitespace_returnsTrimmedGamerTag() throws Exception {
        String gamerTagWithWhitespace = WHITESPACE + VALID_GAMERTAG + WHITESPACE;
        GamerTag expectedGamerTag = new GamerTag(VALID_GAMERTAG);
        assertEquals(expectedGamerTag, ParserUtil.parseGamerTag(gamerTagWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsNormalizedPhone() throws Exception {
        String input = WHITESPACE + "+65   9312-1534" + WHITESPACE;
        Phone expectedPhone = new Phone("+65 9312-1534");
        assertEquals(expectedPhone, ParserUtil.parsePhone(input));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseServer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseServer((String) null));
    }

    @Test
    public void parseServer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseServer(INVALID_SERVER));
    }

    @Test
    public void parseServer_validValueWithoutWhitespace_returnsServer() throws Exception {
        Server expectedServer = new Server(VALID_SERVER);
        assertEquals(expectedServer, ParserUtil.parseServer(VALID_SERVER));
    }

    @Test
    public void parseServer_validValueWithWhitespace_returnsTrimmedServer() throws Exception {
        String serverWithWhitespace = WHITESPACE + VALID_SERVER + WHITESPACE;
        Server expectedServer = new Server(VALID_SERVER);
        assertEquals(expectedServer, ParserUtil.parseServer(serverWithWhitespace));
    }

    @Test
    public void parseCountry_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCountry((String) null));
    }

    @Test
    public void parseCountry_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCountry(INVALID_COUNTRY));
    }

    @Test
    public void parseCountry_whitespaceOnly_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCountry("   "));
    }

    @Test
    public void parseCountry_validValueWithoutWhitespace_returnsCountry() throws Exception {
        Country expectedCountry = new Country(VALID_COUNTRY);
        assertEquals(expectedCountry, ParserUtil.parseCountry(VALID_COUNTRY));
    }

    @Test
    public void parseCountry_validValueWithWhitespace_returnsNormalizedCountry() throws Exception {
        String input = WHITESPACE + "united   states" + WHITESPACE;
        Country expectedCountry = new Country("United States");
        assertEquals(expectedCountry, ParserUtil.parseCountry(input));
    }

    @Test
    public void parseRegion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRegion((String) null));
    }

    @Test
    public void parseRegion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRegion(INVALID_REGION));
    }

    @Test
    public void parseRegion_validValueWithoutWhitespace_returnsRegion() throws Exception {
        Region expectedRegion = new Region(VALID_REGION);
        assertEquals(expectedRegion, ParserUtil.parseRegion(VALID_REGION));
    }

    @Test
    public void parseRegion_validValueWithWhitespace_returnsNormalizedRegion() throws Exception {
        String input = WHITESPACE + "asia" + WHITESPACE;
        Region expectedRegion = new Region("ASIA");
        assertEquals(expectedRegion, ParserUtil.parseRegion(input));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote((String) null));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseMultipleIndexes_validInput_returnsIndexesInOrder() throws Exception {
        ArrayList<Index> indexes = ParserUtil.parseMultipleIndexes("1 2 3");
        assertEquals(3, indexes.size());
        assertEquals(Index.fromOneBased(1), indexes.get(0));
        assertEquals(Index.fromOneBased(2), indexes.get(1));
        assertEquals(Index.fromOneBased(3), indexes.get(2));
    }

    @Test
    public void parseMultipleIndexes_emptyAfterTrim_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("   "));
    }

    @Test
    public void parsePhone_errorMessageIsForwarded() {
        ParseException exception = org.junit.jupiter.api.Assertions.assertThrows(ParseException.class, ()
                -> ParserUtil.parsePhone("12"));
        assertTrue(exception.getMessage().contains("at least 3 digits"));
    }
}
