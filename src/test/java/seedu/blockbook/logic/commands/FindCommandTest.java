package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.blockbook.logic.Messages.MESSAGE_GAMERS_FOUND_BY_FIND;
import static seedu.blockbook.logic.Messages.MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC;
import static seedu.blockbook.logic.Messages.MESSAGE_NO_GAMERS_FOUND_BY_FIND;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;
import static seedu.blockbook.testutil.TypicalGamers.BENSON;
import static seedu.blockbook.testutil.TypicalGamers.CARL;
import static seedu.blockbook.testutil.TypicalGamers.DANIEL;
import static seedu.blockbook.testutil.TypicalGamers.ELLE;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalGamers;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
import seedu.blockbook.model.gamer.SpecificAttributesMatchPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalBlockBook(), new UserPrefs());
    }

    /**
     * Verifies that comparisons work as intended.
     */
    @Test
    public void equals() {
        AnyAttributeContainsKeywordsPredicate firstGlobalPredicate =
                new AnyAttributeContainsKeywordsPredicate("first");
        AnyAttributeContainsKeywordsPredicate secondGlobalPredicate =
                new AnyAttributeContainsKeywordsPredicate("second");

        FindCommand findFirstGlobalCommand = new FindCommand(firstGlobalPredicate);
        FindCommand findSecondGlobalCommand = new FindCommand(secondGlobalPredicate);

        // same object returns true
        assertEquals(findFirstGlobalCommand, findFirstGlobalCommand);

        // same values returns true
        FindCommand findFirstGlobalCommandCopy = new FindCommand(firstGlobalPredicate);
        assertEquals(findFirstGlobalCommand, findFirstGlobalCommandCopy);

        // different types returns false
        assertFalse(findFirstGlobalCommand.equals(1));

        // null returns false
        assertFalse(findFirstGlobalCommand.equals(null));

        // different command returns false
        assertNotEquals(findFirstGlobalCommand, findSecondGlobalCommand);

        SpecificAttributesMatchPredicate firstSpecificPredicate =
                new SpecificAttributesMatchPredicate("Alice", null, null, null, null, null, null, null, null, null);
        SpecificAttributesMatchPredicate secondSpecificPredicate =
                new SpecificAttributesMatchPredicate("Bob", null, null, null, null, null, null, null, null, null);

        FindCommand findFirstSpecificCommand = new FindCommand(firstSpecificPredicate);
        FindCommand findSecondSpecificCommand = new FindCommand(secondSpecificPredicate);

        assertEquals(findFirstSpecificCommand, findFirstSpecificCommand);

        FindCommand findFirstSpecificCommandCopy = new FindCommand(firstSpecificPredicate);
        assertEquals(findFirstSpecificCommand, findFirstSpecificCommandCopy);

        assertNotEquals(findFirstSpecificCommand, findSecondSpecificCommand);
    }

    /**
     * Verifies that global search does not yield a gamer given a keyword that is non-existent.
     */
    @Test
    public void execute_zeroKeywords_noGamerFound() {
        String expectedMessage = MESSAGE_NO_GAMERS_FOUND_BY_FIND;

        // Testing with Global Search format
        AnyAttributeContainsKeywordsPredicate predicate =
                new AnyAttributeContainsKeywordsPredicate("NonExistentKeyword");
        FindCommand command = new FindCommand(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalGamers(), model.getFilteredGamerList());
    }

    /**
     * Verifies that global search yields a gamer at minimum.
     */
    @Test
    public void execute_globalSearch_gamersFound() {
        String expectedMessage = String.format(MESSAGE_GAMERS_FOUND_BY_FIND, 3);

        AnyAttributeContainsKeywordsPredicate predicate =
                new AnyAttributeContainsKeywordsPredicate("me");
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(BENSON, DANIEL, ELLE), model.getFilteredGamerList());
    }

    /**
     * Verifies that a specific search finds a gamer at minimum.
     */
    @Test
    public void execute_specificSearch_gamersFound() {
        // Simulates: find name/Kurz
        String expectedMessage = String.format(MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC, 1, "Name");

        SpecificAttributesMatchPredicate predicate = new SpecificAttributesMatchPredicate(
                "Kurz", null, null, null, null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredGamerList());
    }

    /**
     * Verifies that a specific search finds a gamer given multiple keywords.
     */
    @Test
    public void execute_specificSearchMultipleAttributes_gamersFound() {
        String expectedMessage = String.format(MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC, 1, "Name, Phone");
        SpecificAttributesMatchPredicate predicate = new SpecificAttributesMatchPredicate(
                "Alice", null, "8535", null, null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE), model.getFilteredGamerList());
    }
    /**
     * Verifies that a specific search does not find a gamer given multiple keywords
     * but at least one that does not exist.
     */
    @Test
    public void execute_specificSearchMultipleAttributesOneFails_noGamerFound() {
        // Proves the "AND" logic works: If the name matches but the phone is wrong, it returns 0.
        String expectedMessage = MESSAGE_NO_GAMERS_FOUND_BY_FIND;
        SpecificAttributesMatchPredicate predicate = new SpecificAttributesMatchPredicate(
                "Alice", null, "00000000", null, null, null, null, null, null, null); // 00000000 doesn't exist
        FindCommand command = new FindCommand(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalGamers(), model.getFilteredGamerList());
    }

    /**
     * Verifies that case does not matter in terms of searching for global search.
     */
    @Test
    public void execute_globalSearchCaseInsensitive_gamersFound() {
        // Proves that typing weird casing still finds the gamer
        String expectedMessage = String.format(MESSAGE_GAMERS_FOUND_BY_FIND, 1);
        AnyAttributeContainsKeywordsPredicate predicate = new AnyAttributeContainsKeywordsPredicate("kUrZ");
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredGamerList());
    }

    /**
     * Verifies that case does not matter in terms of searching under specific attribute search.
     */
    @Test
    public void execute_specificSearchCaseInsensitive_gamersFound() {
        String expectedMessage = String.format(MESSAGE_GAMERS_FOUND_BY_FIND_SPECIFIC, 1, "Name");
        SpecificAttributesMatchPredicate predicate = new SpecificAttributesMatchPredicate(
                "kUrZ", null, null, null, null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredGamerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies returning a string of the method.
     */
    @Test
    public void toStringMethod() {
        AnyAttributeContainsKeywordsPredicate predicate = new AnyAttributeContainsKeywordsPredicate("keyword");
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
