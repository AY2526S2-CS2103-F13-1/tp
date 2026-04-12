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

        // invalid overall structure: missing or malformed '@'
        assertFalse(Email.isValidEmail("aliceexample.com"));
        assertFalse(Email.isValidEmail("alice.example.com"));
        assertFalse(Email.isValidEmail("alice@@example.com"));
        assertFalse(Email.isValidEmail("alice@bob@example.com"));
        assertFalse(Email.isValidEmail("alice@"));
        assertFalse(Email.isValidEmail("@example.com"));
        assertFalse(Email.isValidEmail("@"));
        assertFalse(Email.isValidEmail(""));

        // invalid structure: spaces around '@'
        assertFalse(Email.isValidEmail("alice @example.com"));
        assertFalse(Email.isValidEmail("alice@ example.com"));
        assertFalse(Email.isValidEmail("alice @ example.com"));

        // invalid structure: unsupported chars adjacent to '@'
        assertFalse(Email.isValidEmail("alice,@example.com"));
        assertFalse(Email.isValidEmail("alice;@example.com"));
        assertFalse(Email.isValidEmail("alice@,example.com"));
        assertFalse(Email.isValidEmail("alice@;example.com"));

        // invalid local-part: missing local-part entirely
        assertFalse(Email.isValidEmail("@example.com"));

        // invalid local-part: starts with special character
        assertFalse(Email.isValidEmail(".alice@example.com"));
        assertFalse(Email.isValidEmail("+alice@example.com"));
        assertFalse(Email.isValidEmail("_alice@example.com"));
        assertFalse(Email.isValidEmail("-alice@example.com"));

        // invalid local-part: ends with special character
        assertFalse(Email.isValidEmail("alice.@example.com"));
        assertFalse(Email.isValidEmail("alice+@example.com"));
        assertFalse(Email.isValidEmail("alice_@example.com"));
        assertFalse(Email.isValidEmail("alice-@example.com"));

        // invalid local-part: contains unsupported characters
        assertFalse(Email.isValidEmail("ali!ce@example.com"));
        assertFalse(Email.isValidEmail("ali#ce@example.com"));
        assertFalse(Email.isValidEmail("ali ce@example.com"));
        assertFalse(Email.isValidEmail("alice,@example.com"));
        assertFalse(Email.isValidEmail("alice;bob@example.com"));
        assertFalse(Email.isValidEmail("alice:bob@example.com"));
        assertFalse(Email.isValidEmail("alice/bob@example.com"));
        assertFalse(Email.isValidEmail("alice(bob)@example.com"));
        assertFalse(Email.isValidEmail("\"alice\"@example.com"));

        // valid local-part: alphanumeric only
        assertTrue(Email.isValidEmail("alice@example.com"));
        assertTrue(Email.isValidEmail("a1b2c3@example.com"));
        assertTrue(Email.isValidEmail("z@example.com"));
        assertTrue(Email.isValidEmail("A123456789@example.com"));

        // valid local-part: one allowed special character internally
        assertTrue(Email.isValidEmail("alice.bob@example.com"));
        assertTrue(Email.isValidEmail("alice+bob@example.com"));
        assertTrue(Email.isValidEmail("alice_bob@example.com"));
        assertTrue(Email.isValidEmail("alice-bob@example.com"));
        assertTrue(Email.isValidEmail("a.b@example.com"));
        assertTrue(Email.isValidEmail("a+b@example.com"));
        assertTrue(Email.isValidEmail("a_b@example.com"));
        assertTrue(Email.isValidEmail("a-b@example.com"));

        // valid local-part: repeated same special character internally
        assertTrue(Email.isValidEmail("alice..bob@example.com"));
        assertTrue(Email.isValidEmail("alice++bob@example.com"));
        assertTrue(Email.isValidEmail("alice__bob@example.com"));
        assertTrue(Email.isValidEmail("alice--bob@example.com"));

        // valid local-part: mixed allowed special characters internally
        assertTrue(Email.isValidEmail("alice.bob+charlie@example.com"));
        assertTrue(Email.isValidEmail("alice_bob-charlie@example.com"));
        assertTrue(Email.isValidEmail("a+b_c-d.e@example.com"));
        assertTrue(Email.isValidEmail("john_+_-.doe@example.com"));
        assertTrue(Email.isValidEmail("a._+-b@example.com"));
        assertTrue(Email.isValidEmail("abc+_.-def@example.com"));
        assertTrue(Email.isValidEmail("a..b__c--d++e@example.com"));

        // valid local-part: long but still structurally valid
        assertTrue(Email.isValidEmail("abcdefghijklmnopqrstuvwxyz@example.com"));
        assertTrue(Email.isValidEmail("abc123.def456+ghi789_jkl-mno@example.com"));

        // domain must be made up of labels separated by periods
        assertFalse(Email.isValidEmail("alice@example"));
        assertFalse(Email.isValidEmail("alice@"));
        assertFalse(Email.isValidEmail("alice@.com"));
        assertFalse(Email.isValidEmail("alice@example..com"));

        // domain must end with a label at least 2 characters long
        assertFalse(Email.isValidEmail("alice@example.c"));
        assertFalse(Email.isValidEmail("alice@me.a"));

        // each domain label must start and end with alphanumeric characters
        assertFalse(Email.isValidEmail("alice@-example.com"));
        assertFalse(Email.isValidEmail("alice@example-.com"));
        assertFalse(Email.isValidEmail("alice@..example.com"));
        assertFalse(Email.isValidEmail("alice@sub.-example.com"));
        assertFalse(Email.isValidEmail("alice@sub.example-.com"));

        // each domain label may contain only alphanumeric characters and internal hyphens
        assertFalse(Email.isValidEmail("alice@exam_ple.com"));
        assertFalse(Email.isValidEmail("alice@example!.com"));
        assertFalse(Email.isValidEmail("alice@exa%mple.com"));

        // valid emails
        assertTrue(Email.isValidEmail("alice@example.com"));
        assertTrue(Email.isValidEmail("alice.bob+tag@example-domain.com"));
        assertTrue(Email.isValidEmail("a1_b@example.co"));
        assertTrue(Email.isValidEmail("me@nus.edu.sg"));
        assertTrue(Email.isValidEmail("john_doe@sub-domain.example.org"));
        assertTrue(Email.isValidEmail("a@a.cd"));
        assertTrue(Email.isValidEmail("user123@alpha-beta.gamma"));
        assertTrue(Email.isValidEmail("first.last@one-two.three-four.com"));
        assertTrue(Email.isValidEmail("x_y.z+q@a-b.cdef"));
        assertTrue(Email.isValidEmail("name@abc-def.gh"));
        assertTrue(Email.isValidEmail("john_+_-.doe@hotmail.com"));
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
