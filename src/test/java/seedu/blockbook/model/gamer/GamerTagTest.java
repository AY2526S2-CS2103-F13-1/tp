package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GamerTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GamerTag(null));
    }

    @Test
    public void constructor_invalidGamerTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GamerTag("bad tag"));
    }

    @Test
    public void isValidGamerTag() {
        assertThrows(NullPointerException.class, () -> GamerTag.isValidGamerTag(null));

        assertFalse(GamerTag.isValidGamerTag("bad tag"));
        assertFalse(GamerTag.isValidGamerTag("bad@tag"));
        assertFalse(GamerTag.isValidGamerTag("bad/tag"));
        assertFalse(GamerTag.isValidGamerTag("bad-tag"));
        assertFalse(GamerTag.isValidGamerTag("bad!tag"));
        assertFalse(GamerTag.isValidGamerTag("A".repeat(51)));

        assertTrue(GamerTag.isValidGamerTag("Herobrine"));
        assertTrue(GamerTag.isValidGamerTag("12345"));
        assertTrue(GamerTag.isValidGamerTag("Hero123"));
        assertTrue(GamerTag.isValidGamerTag("hero_123"));
        assertTrue(GamerTag.isValidGamerTag("123Hero"));
    }

    @Test
    public void equals() {
        GamerTag gamerTag = new GamerTag("Herobrine");

        assertTrue(gamerTag.equals(new GamerTag("herobrine")));
        assertTrue(gamerTag.equals(new GamerTag("HEROBRINE")));
        assertTrue(gamerTag.equals(new GamerTag("hEROBRINE")));
        assertTrue(gamerTag.equals(gamerTag));

        assertFalse(gamerTag.equals(null));
        assertFalse(gamerTag.equals(5.0f));
        assertFalse(gamerTag.equals(new GamerTag("Herobrine1")));
    }

    @Test
    public void hashCode_sameIgnoringCase_sameHashCode() {
        assertEquals(new GamerTag("Herobrine").hashCode(), new GamerTag("HEROBRINE").hashCode());
    }

    @Test
    public void hashCode_equalObjectsIgnoringCase_sameHashCode() {
        GamerTag g1 = new GamerTag("Herobrine");
        GamerTag g2 = new GamerTag("HEROBRINE");

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }
}
