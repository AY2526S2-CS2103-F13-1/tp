package seedu.blockbook.logic.commands;

import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

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

public class GroupEditCommandTest {

    @Test
    public void execute_renameGroup_success() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index groupIndex = Index.fromOneBased(1);
        Group groupToRename = model.getGroupList().get(groupIndex.getZeroBased());
        Group newGroup = new Group("Alpha Squad");

        GroupEditCommand command = new GroupEditCommand(groupIndex, newGroup);

        List<Group> updatedGroups = new ArrayList<>(model.getGroupList());
        updatedGroups.set(groupIndex.getZeroBased(), newGroup);

        List<Gamer> updatedGamers = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            List<Group> gamerGroups = new ArrayList<>(gamer.getGroups());
            boolean updated = false;
            for (int i = 0; i < gamerGroups.size(); i++) {
                if (gamerGroups.get(i).equals(groupToRename)) {
                    gamerGroups.set(i, newGroup);
                    updated = true;
                }
            }
            if (updated) {
                updatedGamers.add(new Gamer(
                        gamer.getName(),
                        gamer.getGamerTag(),
                        gamer.getPhone(),
                        gamer.getEmail(),
                        gamerGroups,
                        gamer.getServer(),
                        gamer.getFavourite(),
                        gamer.getCountry(),
                        gamer.getRegion(),
                        gamer.getNote()
                ));
            } else {
                updatedGamers.add(gamer);
            }
        }

        BlockBook updatedBlockBook = new BlockBook();
        updatedBlockBook.setGroups(updatedGroups);
        updatedBlockBook.setGamers(updatedGamers);

        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());
        expectedModel.setBlockBook(updatedBlockBook);

        String expectedMessage = String.format(GroupEditCommand.MESSAGE_SUCCESS, groupToRename, newGroup);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBounds = Index.fromOneBased(model.getGroupList().size() + 1);
        Group newGroup = new Group("Alpha Squad");

        GroupEditCommand command = new GroupEditCommand(outOfBounds, newGroup);
        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_duplicateGroupName_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Group existingGroup = new Group("Beta Squad");
        model.addGroup(existingGroup);

        Index groupIndex = Index.fromOneBased(1);
        GroupEditCommand command = new GroupEditCommand(groupIndex, existingGroup);
        assertCommandFailure(command, model, GroupCreateCommand.MESSAGE_DUPLICATE_GROUP);
    }
}
