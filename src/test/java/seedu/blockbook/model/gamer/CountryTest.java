package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CountryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Country(null));
    }

    @Test
    public void constructor_invalidCountry_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Country("Singapore1"));
    }

    @Test
    public void isValidCountry() {
        assertThrows(NullPointerException.class, () -> Country.isValidCountry(null));

        assertFalse(Country.isValidCountry("Singapore1"));
        assertFalse(Country.isValidCountry("United_States"));
        assertFalse(Country.isValidCountry("Singapore!"));
        assertFalse(Country.isValidCountry("A".repeat(51)));
        assertFalse(Country.isValidCountry("Sin/gapore"));

        assertTrue(Country.isValidCountry("Singapore"));
        assertTrue(Country.isValidCountry("New Zealand"));
        assertTrue(Country.isValidCountry("Timor-Leste"));
    }

    @Test
    public void equals() {
        Country country = new Country("Singapore");

        assertTrue(country.equals(new Country("Singapore")));
        assertTrue(country.equals(country));

        assertFalse(country.equals(null));
        assertFalse(country.equals(5.0f));
        assertFalse(country.equals(new Country("Malaysia")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Country c1 = new Country("Japan");
        Country c2 = new Country("Japan");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
