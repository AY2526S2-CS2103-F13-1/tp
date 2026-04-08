package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Note("ranked,ready"));
    }

    @Test
    public void isValidNote() {
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        assertFalse(Note.isValidNote("ranked,ready"));
        assertFalse(Note.isValidNote("ready!"));
        assertFalse(Note.isValidNote("A".repeat(51)));

        assertTrue(Note.isValidNote("ranked"));
        assertTrue(Note.isValidNote("ranked 2"));
        assertTrue(Note.isValidNote("ranked_ready"));
        assertTrue(Note.isValidNote("raid-ready"));
        assertTrue(Note.isValidNote("King's main alt"));
        assertTrue(Note.isValidNote("_-' 2"));
    }

    @Test
    public void equals() {
        Note note = new Note("test_note");

        assertTrue(note.equals(new Note("test_note")));
        assertTrue(note.equals(note));

        assertFalse(note.equals(null));
        assertFalse(note.equals(5.0f));
        assertFalse(note.equals(new Note("other_note")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Note n1 = new Note("test_note");
        Note n2 = new Note("test_note");

        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }
}
