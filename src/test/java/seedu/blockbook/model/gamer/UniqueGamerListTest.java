package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.model.gamer.exceptions.DuplicateGamerException;
import seedu.blockbook.model.gamer.exceptions.GamerNotFoundException;
import seedu.blockbook.testutil.GamerBuilder;

public class UniqueGamerListTest {

    private final UniqueGamerList uniqueGamerList = new UniqueGamerList();

    @Test
    public void contains_nullGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.contains(null));
    }

    @Test
    public void contains_gamerNotInList_returnsFalse() {
        assertFalse(uniqueGamerList.contains(ALICE));
    }

    @Test
    public void contains_gamerInList_returnsTrue() {
        uniqueGamerList.add(ALICE);
        assertTrue(uniqueGamerList.contains(ALICE));
    }

    @Test
    public void contains_gamerWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGamerList.add(ALICE);
        Gamer editedAlice = new GamerBuilder(ALICE)
                .withPhone("91234567")
                .withEmail("alice2@example.com")
                .withGroups("Arena Team")
                .withServer("10.0.0.1:25565")
                .withFavourite(false)
                .withCountry("Malaysia")
                .withRegion("EU")
                .withNote("updated_note")
                .build();
        assertTrue(uniqueGamerList.contains(editedAlice));
    }

    @Test
    public void add_nullGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.add(null));
    }

    @Test
    public void add_duplicateGamer_throwsDuplicateGamerException() {
        uniqueGamerList.add(ALICE);
        assertThrows(DuplicateGamerException.class, () -> uniqueGamerList.add(ALICE));
    }

    @Test
    public void setGamer_nullTargetGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.setGamer(null, ALICE));
    }

    @Test
    public void setGamer_nullEditedGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.setGamer(ALICE, null));
    }

    @Test
    public void setGamer_targetGamerNotInList_throwsGamerNotFoundException() {
        assertThrows(GamerNotFoundException.class, () -> uniqueGamerList.setGamer(ALICE, ALICE));
    }

    @Test
    public void setGamer_editedGamerIsSameGamer_success() {
        uniqueGamerList.add(ALICE);
        uniqueGamerList.setGamer(ALICE, ALICE);

        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        expectedUniqueGamerList.add(ALICE);
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamer_editedGamerHasSameIdentity_success() {
        uniqueGamerList.add(ALICE);
        Gamer editedAlice = new GamerBuilder(ALICE)
                .withPhone("91234567")
                .withEmail("alice2@example.com")
                .withGroups("Arena Team")
                .withServer("10.0.0.1:25565")
                .withFavourite(false)
                .withCountry("Malaysia")
                .withRegion("EU")
                .withNote("updated_note")
                .build();

        uniqueGamerList.setGamer(ALICE, editedAlice);

        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        expectedUniqueGamerList.add(editedAlice);
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamer_editedGamerHasDifferentIdentity_success() {
        uniqueGamerList.add(ALICE);
        uniqueGamerList.setGamer(ALICE, BENSON);

        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        expectedUniqueGamerList.add(BENSON);
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamer_editedGamerHasNonUniqueIdentity_throwsDuplicateGamerException() {
        uniqueGamerList.add(ALICE);
        uniqueGamerList.add(BENSON);
        assertThrows(DuplicateGamerException.class, () -> uniqueGamerList.setGamer(ALICE, BENSON));
    }

    @Test
    public void remove_nullGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.remove(null));
    }

    @Test
    public void remove_gamerDoesNotExist_throwsGamerNotFoundException() {
        assertThrows(GamerNotFoundException.class, () -> uniqueGamerList.remove(ALICE));
    }

    @Test
    public void remove_existingGamer_removesGamer() {
        uniqueGamerList.add(ALICE);
        uniqueGamerList.remove(ALICE);

        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamers_nullUniqueGamerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.setGamers((UniqueGamerList) null));
    }

    @Test
    public void setGamers_uniqueGamerList_replacesOwnListWithProvidedUniqueGamerList() {
        uniqueGamerList.add(ALICE);
        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        expectedUniqueGamerList.add(BENSON);

        uniqueGamerList.setGamers(expectedUniqueGamerList);
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGamerList.setGamers((List<Gamer>) null));
    }

    @Test
    public void setGamers_list_replacesOwnListWithProvidedList() {
        uniqueGamerList.add(ALICE);
        List<Gamer> gamerList = Collections.singletonList(BENSON);

        uniqueGamerList.setGamers(gamerList);

        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
        expectedUniqueGamerList.add(BENSON);
        assertEquals(expectedUniqueGamerList, uniqueGamerList);
    }

    @Test
    public void setGamers_listWithDuplicateGamers_throwsDuplicateGamerException() {
        List<Gamer> listWithDuplicateGamers = Arrays.asList(ALICE, new GamerBuilder(ALICE)
                .withPhone("91234567")
                .withEmail("alice2@example.com")
                .withGroups("Arena Team")
                .withServer("10.0.0.1:25565")
                .withFavourite(false)
                .withCountry("Malaysia")
                .withRegion("EU")
                .withNote("updated_note")
                .build());

        assertThrows(DuplicateGamerException.class, () -> uniqueGamerList.setGamers(listWithDuplicateGamers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueGamerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGamerList.asUnmodifiableObservableList().toString(), uniqueGamerList.toString());
    }
}
