package seedu.blockbook.logic.commands;

import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;

public class GroupViewCommandTest {

    @Test
    public void execute_groupWithGamers_success() {
        Group group = new Group("Alpha");
        Gamer gamerInGroup = new Gamer(null, new GamerTag("AlphaTag"), null, null,
                List.of(group), null, new Favourite(false), null, null, null);
        Gamer otherGamer = new Gamer(null, new GamerTag("OtherTag"), null, null,
                List.of(), null, new Favourite(false), null, null, null);

        BlockBook blockBook = new BlockBook();
        blockBook.addGroup(group);
        blockBook.addGamer(gamerInGroup);
        blockBook.addGamer(otherGamer);

        Model model = new ModelManager(blockBook, new UserPrefs());
        Model expectedModel = new ModelManager(blockBook, new UserPrefs());
        expectedModel.updateFilteredGamerList(gamer ->
                gamer.getGroups().stream().anyMatch(g -> g.toString().equalsIgnoreCase(group.toString())));

        GroupViewCommand command = new GroupViewCommand(Index.fromOneBased(1));
        String expectedMessage = String.format(GroupViewCommand.MESSAGE_SUCCESS,
                group, gamerInGroup.getGamerTag().toString());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupWithNoGamers_showsNoGamersMessage() {
        Group group = new Group("Alpha");
        Gamer otherGamer = new Gamer(null, new GamerTag("OtherTag"), null, null,
                List.of(), null, new Favourite(false), null, null, null);

        BlockBook blockBook = new BlockBook();
        blockBook.addGroup(group);
        blockBook.addGamer(otherGamer);

        Model model = new ModelManager(blockBook, new UserPrefs());
        Model expectedModel = new ModelManager(blockBook, new UserPrefs());
        expectedModel.updateFilteredGamerList(gamer ->
                gamer.getGroups().stream().anyMatch(g -> g.toString().equalsIgnoreCase(group.toString())));

        GroupViewCommand command = new GroupViewCommand(Index.fromOneBased(1));
        String expectedMessage = String.format(GroupViewCommand.MESSAGE_NO_GAMERS, group);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        BlockBook blockBook = new BlockBook();
        blockBook.addGroup(new Group("Alpha"));
        Model model = new ModelManager(blockBook, new UserPrefs());

        GroupViewCommand command = new GroupViewCommand(Index.fromOneBased(2));
        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }
}
