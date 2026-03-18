package seedu.blockbook.logic.commands;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.blockbook.logic.Messages.MESSAGE_GAMERS_LISTED_COUNT;
//import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.blockbook.testutil.TypicalGamers.CARL;
//import static seedu.blockbook.testutil.TypicalGamers.ELLE;
//import static seedu.blockbook.testutil.TypicalGamers.FIONA;
//import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

//import java.util.Arrays;
//import java.util.Collections;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.blockbook.model.Model;
//import seedu.blockbook.model.ModelManager;
//import seedu.blockbook.model.UserPrefs;
//import seedu.blockbook.model.gamer.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
//    private Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
//    private Model expectedModel = new ModelManager(getTypicalBlockBook(), new UserPrefs());
//
//    @Test
//    public void equals() {
//        NameContainsKeywordsPredicate firstPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
//        NameContainsKeywordsPredicate secondPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
//
//        FindCommand findFirstCommand = new FindCommand(firstPredicate);
//        FindCommand findSecondCommand = new FindCommand(secondPredicate);
//
//        // same object -> returns true
//        assertTrue(findFirstCommand.equals(findFirstCommand));
//
//        // same values -> returns true
//        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
//        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(findFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(findFirstCommand.equals(null));
//
    //        // different gamer -> returns false
//        assertFalse(findFirstCommand.equals(findSecondCommand));
//    }
//
//    @Test
//    public void execute_zeroKeywords_noGamerFound() {
//        String expectedMessage = String.format(MESSAGE_GAMERS_LISTED_COUNT, 0);
//        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
//        FindCommand command = new FindCommand(predicate);
//        expectedModel.updateFilteredGamerList(predicate);
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//        assertEquals(Collections.emptyList(), model.getFilteredGamerList());
//    }
//
//    @Test
//    public void execute_multipleKeywords_multipleGamersFound() {
//        String expectedMessage = String.format(MESSAGE_GAMERS_LISTED_COUNT, 3);
//        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
//        FindCommand command = new FindCommand(predicate);
//        expectedModel.updateFilteredGamerList(predicate);
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredGamerList());
//    }
//
//    @Test
//    public void toStringMethod() {
//        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
//        FindCommand findCommand = new FindCommand(predicate);
//        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
//        assertEquals(expected, findCommand.toString());
//    }
//
//    /**
//     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
//     */
//    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
//        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
//    }
}



