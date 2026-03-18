package seedu.blockbook.model;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.blockbook.testutil.Assert.assertThrows;
//import static seedu.blockbook.testutil.TypicalGamers.ALICE;
//import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.blockbook.model.Gamer.Gamer;
//import seedu.blockbook.model.Gamer.exceptions.DuplicateGamerException;
//import seedu.blockbook.testutil.GamerBuilder;

public class BlockBookTest {

//    private final BlockBook BlockBook = new BlockBook();
//
//    @Test
//    public void constructor() {
//        assertEquals(Collections.emptyList(), BlockBook.getGamerList());
//    }
//
//    @Test
//    public void resetData_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> BlockBook.resetData(null));
//    }
//
//    @Test
//    public void resetData_withValidReadOnlyBlockBook_replacesData() {
//        BlockBook newData = getTypicalBlockBook();
//        BlockBook.resetData(newData);
//        assertEquals(newData, BlockBook);
//    }
//
//    @Test
//    public void resetData_withDuplicateGamers_throwsDuplicateGamerException() {
//        // Two gamers with the same identity fields
//        Gamer editedAlice = new GamerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        List<Gamer> newGamers = Arrays.asList(ALICE, editedAlice);
//        BlockBookStub newData = new BlockBookStub(newGamers);
//
//        assertThrows(DuplicateGamerException.class, () -> BlockBook.resetData(newData));
//    }
//
//    @Test
//    public void hasGamer_nullGamer_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> BlockBook.hasGamer(null));
//    }
//
//    @Test
//    public void hasGamer_GamerNotInBlockBook_returnsFalse() {
//        assertFalse(BlockBook.hasGamer(ALICE));
//    }
//
//    @Test
//    public void hasGamer_GamerInBlockBook_returnsTrue() {
//        BlockBook.addGamer(ALICE);
//        assertTrue(BlockBook.hasGamer(ALICE));
//    }
//
//    @Test
//    public void hasGamer_GamerWithSameIdentityFieldsInBlockBook_returnsTrue() {
//        BlockBook.addGamer(ALICE);
//        Gamer editedAlice = new GamerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        assertTrue(BlockBook.hasGamer(editedAlice));
//    }
//
//    @Test
//    public void getGamerList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, () -> BlockBook.getGamerList().remove(0));
//    }
//
//    @Test
//    public void toStringMethod() {
//        String expected = BlockBook.class.getCanonicalName() + "{gamers=" + BlockBook.getGamerList() + "}";
//        assertEquals(expected, BlockBook.toString());
//    }
//
//    /**
//     * A stub ReadOnlyBlockBook whose gamers list can violate interface constraints.
//     */
//    private static class BlockBookStub implements ReadOnlyBlockBook {
//        private final ObservableList<Gamer> gamers = FXCollections.observableArrayList();
//
//        BlockBookStub(Collection<Gamer> gamers) {
//            this.gamers.setAll(gamers);
//        }
//
//        @Override
//        public ObservableList<Gamer> getGamerList() {
//            return gamers;
//        }
//    }

}



