package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalGamers.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.ReadOnlyUserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.GamerBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullGamer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_gamerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGamerAdded modelStub = new ModelStubAcceptingGamerAdded();
        Gamer validGamer = new GamerBuilder().build();

        CommandResult commandResult = new AddCommand(validGamer).execute(modelStub);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.format(validGamer));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGamer), modelStub.gamersAdded);
    }

    @Test
    public void execute_duplicateGamer_throwsCommandException() {
        Gamer validGamer = new GamerBuilder().build();
        AddCommand addCommand = new AddCommand(validGamer);
        ModelStub modelStub = new ModelStubWithGamer(validGamer);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_GAMERTAG, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Gamer alice = new GamerBuilder().withName("Alice").withGamerTag("alice_tag").build();
        Gamer bob = new GamerBuilder().withName("Bob").withGamerTag("bob_tag").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different gamer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBlockBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBlockBookFilePath(Path blockBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBlockBook(ReadOnlyBlockBook blockBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBlockBook getBlockBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGamer(Gamer gamer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGamer(Gamer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGamer(Gamer gamer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGamer(Gamer target, Gamer editedGamer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Gamer> getFilteredGamerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGamerList(Predicate<Gamer> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single gamer.
     */
    private class ModelStubWithGamer extends ModelStub {
        private final Gamer gamer;

        ModelStubWithGamer(Gamer gamer) {
            requireNonNull(gamer);
            this.gamer = gamer;
        }

        @Override
        public boolean hasGamer(Gamer gamer) {
            requireNonNull(gamer);
            return this.gamer.isSameGamer(gamer);
        }
    }

    /**
     * A Model stub that always accepts the gamer being added.
     */
    private class ModelStubAcceptingGamerAdded extends ModelStub {
        final ArrayList<Gamer> gamersAdded = new ArrayList<>();

        @Override
        public boolean hasGamer(Gamer gamer) {
            requireNonNull(gamer);
            return gamersAdded.stream().anyMatch(gamer::isSameGamer);
        }

        @Override
        public void addGamer(Gamer gamer) {
            requireNonNull(gamer);
            gamersAdded.add(gamer);
        }

        @Override
        public ReadOnlyBlockBook getBlockBook() {
            return new BlockBook();
        }
    }
}
