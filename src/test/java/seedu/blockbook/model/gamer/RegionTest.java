package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Region(null));
    }

    @Test
    public void constructor_invalidRegion_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Region("antarctica"));
    }

    @Test
    public void constructor_lowercaseRegion_storesUppercase() {
        assertEquals("ASIA", new Region("asia").toString());
    }

    @Test
    public void isValidRegion() {
        assertThrows(NullPointerException.class, () -> Region.isValidRegion(null));

        assertFalse(Region.isValidRegion("oce"));
        assertFalse(Region.isValidRegion("antarctica"));
        assertFalse(Region.isValidRegion("N A"));

        assertTrue(Region.isValidRegion("NA"));
        assertTrue(Region.isValidRegion("SA"));
        assertTrue(Region.isValidRegion("EU"));
        assertTrue(Region.isValidRegion("AFRICA"));
        assertTrue(Region.isValidRegion("ASIA"));
        assertTrue(Region.isValidRegion("OCEANIA"));
        assertTrue(Region.isValidRegion("ME"));
        assertTrue(Region.isValidRegion("asia"));
        assertTrue(Region.isValidRegion("me"));
    }

    @Test
    public void equals() {
        Region region = new Region("ASIA");

        assertTrue(region.equals(new Region("ASIA")));
        assertTrue(region.equals(region));

        assertFalse(region.equals(null));
        assertFalse(region.equals(5.0f));
        assertFalse(region.equals(new Region("EU")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Region r1 = new Region("ASIA");
        Region r2 = new Region("ASIA");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
