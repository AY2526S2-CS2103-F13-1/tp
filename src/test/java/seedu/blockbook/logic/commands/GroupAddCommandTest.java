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
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.testutil.GamerBuilder;

public class GroupAddCommandTest {

    @Test
    public void execute_addGroup_success() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Group newGroup = new Group("Alpha");
        model.addGroup(newGroup);

        Index gamerIndex = INDEX_FIRST_GAMER;
        Index groupIndex = Index.fromOneBased(model.getGroupList().size());
        GroupAddCommand command = new GroupAddCommand(gamerIndex, groupIndex);

        Gamer gamerToEdit = model.getFilteredGamerList().get(gamerIndex.getZeroBased());
        List<Group> updatedGroups = new ArrayList<>(gamerToEdit.getGroups());
        updatedGroups.add(newGroup);

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

        String expectedMessage = String.format(GroupAddCommand.MESSAGE_SUCCESS,
                gamerToEdit.getGamerTag(), newGroup);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingGroup_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index gamerIndex = INDEX_FIRST_GAMER;
        Index groupIndex = Index.fromOneBased(1);

        GroupAddCommand command = new GroupAddCommand(gamerIndex, groupIndex);
        assertCommandFailure(command, model, GroupAddCommand.MESSAGE_ALREADY_IN_GROUP);
    }

    @Test
    public void execute_emptyGamerList_throwsCommandException() {
        Model model = new ModelManager(new BlockBook(), new UserPrefs());
        GroupAddCommand command = new GroupAddCommand(INDEX_FIRST_GAMER, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_EMPTY_CONTACT_LIST);
    }

    @Test
    public void execute_emptyGroupList_throwsCommandException() {
        BlockBook blockBook = new BlockBook();
        Gamer gamer = new GamerBuilder().withGroups().build();
        blockBook.addGamer(gamer);

        Model model = new ModelManager(blockBook, new UserPrefs());
        GroupAddCommand command = new GroupAddCommand(INDEX_FIRST_GAMER, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_invalidGamerIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundGamer = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        Index groupIndex = Index.fromOneBased(1);

        GroupAddCommand command = new GroupAddCommand(outOfBoundGamer, groupIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundGroup = Index.fromOneBased(model.getGroupList().size() + 1);

        GroupAddCommand command = new GroupAddCommand(INDEX_FIRST_GAMER, outOfBoundGroup);
        assertCommandFailure(command, model, Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_invalidGamerAndGroupIndexes_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBoundGamer = Index.fromOneBased(model.getFilteredGamerList().size() + 1);
        Index outOfBoundGroup = Index.fromOneBased(model.getGroupList().size() + 1);

        GroupAddCommand command = new GroupAddCommand(outOfBoundGamer, outOfBoundGroup);
        assertCommandFailure(command, model, Messages.MESSAGE_GAMER_INDEX_OUT_OF_RANGE + "\n"
                + Messages.MESSAGE_BLOCKBOOK_GROUP_INDEX_OUT_OF_RANGE);
    }
}
