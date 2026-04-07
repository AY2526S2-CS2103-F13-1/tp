package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
    }

    @Test
    public void isValidEmail() {
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        assertFalse(Email.isValidEmail("@example.com"));
        assertFalse(Email.isValidEmail("alice@"));
        assertFalse(Email.isValidEmail("alice@.com"));
        assertFalse(Email.isValidEmail("alice@example.c"));
        assertFalse(Email.isValidEmail(".alice@example.com"));
        assertFalse(Email.isValidEmail("alice.@example.com"));
        assertFalse(Email.isValidEmail("alice@example..com"));

        assertTrue(Email.isValidEmail("alice@example"));
        assertTrue(Email.isValidEmail("alice@example.com"));
        assertTrue(Email.isValidEmail("alice.bob+tag@example-domain.com"));
        assertTrue(Email.isValidEmail("a1_b@example.co"));
    }

    @Test
    public void isValidLaxEmail() {
        assertThrows(NullPointerException.class, () -> Email.isValidLaxEmail(null));

        assertFalse(Email.isValidLaxEmail("alice!example.com"));
        assertFalse(Email.isValidLaxEmail("alice@example,com"));

        assertTrue(Email.isValidLaxEmail("alice@example"));
        assertTrue(Email.isValidLaxEmail("alice+tag@example-domain.com"));
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email.com");

        assertTrue(email.equals(new Email("valid@email.com")));
        assertTrue(email.equals(email));

        assertFalse(email.equals(null));
        assertFalse(email.equals(5.0f));
        assertFalse(email.equals(new Email("other@email.com")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Email e1 = new Email("alice@example.com");
        Email e2 = new Email("alice@example.com");

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
