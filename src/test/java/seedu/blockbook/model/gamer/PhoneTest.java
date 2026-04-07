package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("12"));
    }

    @Test
    public void isValidPhone() {
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        assertFalse(Phone.isValidPhone("+ "));
        assertFalse(Phone.isValidPhone("12"));
        assertFalse(Phone.isValidPhone("abc"));
        assertFalse(Phone.isValidPhone("++123"));
        assertFalse(Phone.isValidPhone("12+345"));
        assertFalse(Phone.isValidPhone("12345+"));
        assertFalse(Phone.isValidPhone("+ 12345"));
        assertFalse(Phone.isValidPhone("+-12345"));
        assertFalse(Phone.isValidPhone("-12345"));
        assertFalse(Phone.isValidPhone("12345-"));
        assertFalse(Phone.isValidPhone("123--456"));
        assertFalse(Phone.isValidPhone("123 -456"));
        assertFalse(Phone.isValidPhone("123- 456"));
        assertFalse(Phone.isValidPhone("1234567890123456"));
        assertFalse(Phone.isValidPhone("1".repeat(31)));

        assertTrue(Phone.isValidPhone("911"));
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("123 456 789"));
        assertTrue(Phone.isValidPhone("9312-1534"));
        assertTrue(Phone.isValidPhone("+65 9312-1534"));
    }

    @Test
    public void getPhoneValidationError() {
        assertThrows(NullPointerException.class, () -> Phone.getPhoneValidationError(null));

        assertTrue(Phone.getPhoneValidationError("12").contains("at least 3 digits"));
        assertTrue(Phone.getPhoneValidationError("12+345").contains("beginning"));
        assertTrue(Phone.getPhoneValidationError("123--456").contains("between digits only"));
        assertTrue(Phone.getPhoneValidationError("+ 12345").contains("followed by a digit"));
        assertTrue(Phone.getPhoneValidationError("+-12345").contains("followed by a digit"));
        assertNull(Phone.getPhoneValidationError("123"));
    }

    @Test
    public void isValidLaxPhone() {
        assertThrows(NullPointerException.class, () -> Phone.isValidLaxPhone(null));

        assertFalse(Phone.isValidLaxPhone("abc"));
        assertFalse(Phone.isValidLaxPhone("123_ext"));

        assertTrue(Phone.isValidLaxPhone("9312-1534"));
        assertTrue(Phone.isValidLaxPhone("+919"));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        assertTrue(phone.equals(new Phone("999")));
        assertTrue(phone.equals(phone));

        assertFalse(phone.equals(null));
        assertFalse(phone.equals(5.0f));
        assertFalse(phone.equals(new Phone("995")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Phone p1 = new Phone("91234567");
        Phone p2 = new Phone("91234567");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
