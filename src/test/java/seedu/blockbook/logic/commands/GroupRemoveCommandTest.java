package seedu.blockbook.logic.commands;

import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

public class GroupRemoveCommandTest {

    @Test
    public void execute_removeGroup_success() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());

        Index gamerIndex = INDEX_FIRST_GAMER;
        Index groupIndex = Index.fromOneBased(1);
        GroupRemoveCommand command = new GroupRemoveCommand(gamerIndex, groupIndex);

        Gamer gamerToEdit = model.getFilteredGamerList().get(gamerIndex.getZeroBased());
        Group groupToRemove = gamerToEdit.getGroups().get(groupIndex.getZeroBased());
        List<Group> updatedGroups = new ArrayList<>(gamerToEdit.getGroups());
        updatedGroups.remove(groupIndex.getZeroBased());

        Gamer updatedGamer = new Gamer(
                gamerToEdit.getName(),
                gamerToEdit.getGamerTag(),
                gamerToEdit.getPhone(),
                gamerToEdit.getEmail(),
                updatedGroups,
                gamerToEdit.getServer(),
                gamerToEdit.getFavourite(),
                gamerToEdit.getCountry(),
                gamerToEdit.getRegion(),
                gamerToEdit.getNote()
        );

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setGamer(gamerToEdit, updatedGamer);

        String expectedMessage = String.format(GroupRemoveCommand.MESSAGE_SUCCESS,
                gamerToEdit.getGamerTag(), groupToRemove);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGamerIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundGamer = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        Index groupIndex = Index.fromOneBased(1);

        GroupRemoveCommand command = new GroupRemoveCommand(outOfBoundGamer, groupIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundGroup = Index.fromOneBased(
                model.getFilteredGamerList().get(INDEX_FIRST_GAMER.getZeroBased()).getGroups().size() + 1);

        GroupRemoveCommand command = new GroupRemoveCommand(INDEX_FIRST_GAMER, outOfBoundGroup);
        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }
}
