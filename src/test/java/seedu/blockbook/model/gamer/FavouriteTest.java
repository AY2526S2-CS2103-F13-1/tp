package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FavouriteTest {

    @Test
    public void isFav() {
        assertTrue(new Favourite(true).isFav());
        assertFalse(new Favourite(false).isFav());
    }

    @Test
    public void toStringMethod() {
        assertEquals("Yes", new Favourite(true).toString());
        assertEquals("No", new Favourite(false).toString());
    }

    @Test
    public void equals() {
        Favourite favourite = new Favourite(true);

        assertTrue(favourite.equals(new Favourite(true)));
        assertTrue(favourite.equals(favourite));

        assertFalse(favourite.equals(null));
        assertFalse(favourite.equals(1));
        assertFalse(favourite.equals(new Favourite(false)));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Favourite f1 = new Favourite(true);
        Favourite f2 = new Favourite(true);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

}
