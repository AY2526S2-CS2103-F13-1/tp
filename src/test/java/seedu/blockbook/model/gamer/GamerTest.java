package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.testutil.GamerBuilder;

public class GamerTest {

    @Test
    public void getGroups_modifyList_throwsUnsupportedOperationException() {
        Gamer gamer = new GamerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> gamer.getGroups().remove(0));
    }

    @Test
    public void isSameGamer() {
        assertTrue(ALICE.isSameGamer(ALICE));

        assertFalse(ALICE.isSameGamer(null));

        Gamer editedAlice = new GamerBuilder(ALICE)
                .withName("Alice Pauline")
                .withPhone("91234567")
                .withEmail("alice2@example.com")
                .withGroups("Arena Team")
                .withServer("10.0.0.1:25565")
                .withFavourite(false)
                .withCountry("Malaysia")
                .withRegion("EU")
                .withNote("updated_note")
                .build();
        assertTrue(ALICE.isSameGamer(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withGamerTag("heroBrine").build();
        assertTrue(ALICE.isSameGamer(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withGamerTag("DifferentTag").build();
        assertFalse(ALICE.isSameGamer(editedAlice));

        Gamer editedBenson = new GamerBuilder(BENSON).withGamerTag("herobrine2x").build();
        assertFalse(BENSON.isSameGamer(editedBenson));
    }

    @Test
    public void equals() {
        Gamer aliceCopy = new GamerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        assertTrue(ALICE.equals(ALICE));

        assertFalse(ALICE.equals(null));

        assertFalse(ALICE.equals(5));

        assertFalse(ALICE.equals(BENSON));

        Gamer editedAlice = new GamerBuilder(ALICE).withName("Alicia Pauline").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withGamerTag("DifferentTag").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withPhone("91234567").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withEmail("alice2@example.com").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withGroups("Arena Team").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withServer("10.0.0.1:25565").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withFavourite(false).build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withCountry("Malaysia").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withRegion("EU").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new GamerBuilder(ALICE).withNote("updated_note").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void constructor_withGroupList_setsFieldsCorrectly() {
        List<Group> groups = List.of(new Group("Raid Team"), new Group("Arena Team"));
        Gamer gamer = new Gamer(
                new Name("Alice Pauline"),
                new GamerTag("HerobrineX"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                groups,
                new Server("127.0.0.1:8080"),
                new Favourite(true),
                new Country("Singapore"),
                new Region("ASIA"),
                new Note("test_note")
        );

        assertEquals(groups, gamer.getGroups());
        assertEquals(new Group("Raid Team"), gamer.getGroups().get(0));
    }

    @Test
    public void toStringMethod() {
        Group firstGroup = ALICE.getGroups().isEmpty() ? null : ALICE.getGroups().get(0);
        String expected = Gamer.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", gamertag=" + ALICE.getGamerTag()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", group=" + firstGroup
                + ", groups=" + ALICE.getGroups()
                + ", server=" + ALICE.getServer()
                + ", favourite=" + ALICE.getFavourite()
                + ", country=" + ALICE.getCountry()
                + ", region=" + ALICE.getRegion()
                + ", note=" + ALICE.getNote()
                + "}";

        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Gamer gamer1 = new GamerBuilder(ALICE).build();
        Gamer gamer2 = new GamerBuilder(ALICE).build();

        assertEquals(gamer1, gamer2);
        assertEquals(gamer1.hashCode(), gamer2.hashCode());
    }

    @Test
    public void hashCode_differentObjects_differentHashCode() {
        Gamer gamer1 = new GamerBuilder(ALICE).build();
        Gamer gamer2 = new GamerBuilder(ALICE).withGamerTag("DifferentTag").build();

        assertNotEquals(gamer1, gamer2);
        assertNotEquals(gamer1.hashCode(), gamer2.hashCode());
    }
}
