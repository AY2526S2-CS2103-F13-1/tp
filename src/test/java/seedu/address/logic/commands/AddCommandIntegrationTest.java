package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.gamer.Gamer;
import seedu.address.testutil.GamerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
    }

    @Test
    public void execute_newGamer_success() {
        Gamer validGamer = new GamerBuilder().build();

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.addGamer(validGamer);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS,
                validGamer.getName() + ", " + validGamer.getGamerTag());

        assertCommandSuccess(new AddCommand(validGamer), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGamer_throwsCommandException() {
        Gamer gamerInList = model.getBlockBook().getGamerList().get(0);
        assertCommandFailure(new AddCommand(gamerInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }
}
