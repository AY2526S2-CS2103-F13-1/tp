package seedu.blockbook.model.gamer;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.blockbook.testutil.Assert.assertThrows;
//import static seedu.blockbook.testutil.TypicalGamers.ALICE;
//import static seedu.blockbook.testutil.TypicalGamers.BOB;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.blockbook.model.gamer.exceptions.DuplicateGamerException;
//import seedu.blockbook.model.gamer.exceptions.GamerNotFoundException;
//import seedu.blockbook.testutil.GamerBuilder;

public class UniqueGamerListTest {

//    private final UniqueGamerList UniqueGamerList = new UniqueGamerList();
//
//    @Test
//    public void contains_nullGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.contains(null));
//    }
//
//    @Test
//    public void contains_GamerNotInList_returnsFalse() {
//        assertFalse(UniqueGamerList.contains(ALICE));
//    }
//
//    @Test
//    public void contains_GamerInList_returnsTrue() {
//        UniqueGamerList.add(ALICE);
//        assertTrue(UniqueGamerList.contains(ALICE));
//    }
//
//    @Test
//    public void contains_GamerWithSameIdentityFieldsInList_returnsTrue() {
//        UniqueGamerList.add(ALICE);
//        Gamer editedAlice = new GamerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        assertTrue(UniqueGamerList.contains(editedAlice));
//    }
//
//    @Test
//    public void add_nullGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.add(null));
//    }
//
//    @Test
//    public void add_duplicateGamer_throwsDuplicateGamerException() {
//        UniqueGamerList.add(ALICE);
//        assertThrows(DuplicateGamerException.class, () -> UniqueGamerList.add(ALICE));
//    }
//
//    @Test
//    public void setGamer_nullTargetGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.setGamer(null, ALICE));
//    }
//
//    @Test
//    public void setGamer_nullEditedGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.setGamer(ALICE, null));
//    }
//
//    @Test
//    public void setGamer_targetGamerNotInList_throwsGamerNotFoundException() {
//        assertThrows(GamerNotFoundException.class, () -> UniqueGamerList.setGamer(ALICE, ALICE));
//    }
//
//    @Test
//    public void setGamer_editedGamerisSameGamer_success() {
//        UniqueGamerList.add(ALICE);
//        UniqueGamerList.setGamer(ALICE, ALICE);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        expectedUniqueGamerList.add(ALICE);
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamer_editedGamerHasSameIdentity_success() {
//        UniqueGamerList.add(ALICE);
//        Gamer editedAlice = new GamerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        UniqueGamerList.setGamer(ALICE, editedAlice);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        expectedUniqueGamerList.add(editedAlice);
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamer_editedGamerHasDifferentIdentity_success() {
//        UniqueGamerList.add(ALICE);
//        UniqueGamerList.setGamer(ALICE, BOB);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        expectedUniqueGamerList.add(BOB);
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamer_editedGamerHasNonUniqueIdentity_throwsDuplicateGamerException() {
//        UniqueGamerList.add(ALICE);
//        UniqueGamerList.add(BOB);
//        assertThrows(DuplicateGamerException.class, () -> UniqueGamerList.setGamer(ALICE, BOB));
//    }
//
//    @Test
//    public void remove_nullGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.remove(null));
//    }
//
//    @Test
//    public void remove_GamerDoesNotExist_throwsGamerNotFoundException() {
//        assertThrows(GamerNotFoundException.class, () -> UniqueGamerList.remove(ALICE));
//    }
//
//    @Test
//    public void remove_existingGamer_removesGamer() {
//        UniqueGamerList.add(ALICE);
//        UniqueGamerList.remove(ALICE);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamers_nullUniqueGamerList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.setGamers((UniqueGamerList) null));
//    }
//
//    @Test
//    public void setGamers_UniqueGamerList_replacesOwnListWithProvidedUniqueGamerList() {
//        UniqueGamerList.add(ALICE);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        expectedUniqueGamerList.add(BOB);
//        UniqueGamerList.setGamers(expectedUniqueGamerList);
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamers_nullList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> UniqueGamerList.setGamers((List<Gamer>) null));
//    }
//
//    @Test
//    public void setGamers_list_replacesOwnListWithProvidedList() {
//        UniqueGamerList.add(ALICE);
//        List<Gamer> GamerList = Collections.singletonList(BOB);
//        UniqueGamerList.setGamers(GamerList);
//        UniqueGamerList expectedUniqueGamerList = new UniqueGamerList();
//        expectedUniqueGamerList.add(BOB);
//        assertEquals(expectedUniqueGamerList, UniqueGamerList);
//    }
//
//    @Test
//    public void setGamers_listWithDuplicateGamers_throwsDuplicateGamerException() {
//        List<Gamer> listWithDuplicateGamers = Arrays.asList(ALICE, ALICE);
//        assertThrows(DuplicateGamerException.class, () -> UniqueGamerList.setGamers(listWithDuplicateGamers));
//    }
//
//    @Test
//    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, ()
//            -> UniqueGamerList.asUnmodifiableObservableList().remove(0));
//    }
//
//    @Test
//    public void toStringMethod() {
//        assertEquals(UniqueGamerList.asUnmodifiableObservableList().toString(), UniqueGamerList.toString());
//    }
}



