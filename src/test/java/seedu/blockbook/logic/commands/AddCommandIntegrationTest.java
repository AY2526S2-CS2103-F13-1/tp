package seedu.blockbook.logic.commands;

import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.testutil.GamerBuilder;

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
        Gamer validGamer = new GamerBuilder().withGamerTag("unique_tag").build();

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.addGamer(validGamer);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.format(validGamer));

        assertCommandSuccess(new AddCommand(validGamer), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGamer_throwsCommandException() {
        Gamer gamerInList = model.getBlockBook().getGamerList().get(0);
        assertCommandFailure(new AddCommand(gamerInList), model, AddCommand.MESSAGE_DUPLICATE_GAMERTAG);
    }
}
