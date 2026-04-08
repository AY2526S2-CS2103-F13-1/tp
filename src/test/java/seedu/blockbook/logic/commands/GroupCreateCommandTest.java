package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Group;

public class GroupCreateCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupCreateCommand(null));
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        Model model = new ModelManager();
        Group validGroup = new Group("Raid Team");

        CommandResult commandResult = new GroupCreateCommand(validGroup).execute(model);

        String expectedMessage = String.format(GroupCreateCommand.MESSAGE_SUCCESS, validGroup);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(1, model.getGroupList().size());
        assertEquals(validGroup, model.getGroupList().get(0));
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group validGroup = new Group("Raid Team");
        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(validGroup);
        Model model = new ModelManager(new BlockBook(), new UserPrefs());
        model.addGroup(validGroup);

        assertThrows(CommandException.class,
                GroupCreateCommand.MESSAGE_DUPLICATE_GROUP, () -> groupCreateCommand.execute(model));
    }

    @Test
    public void equals() {
        GroupCreateCommand addRaidCommand = new GroupCreateCommand(new Group("Raid Team"));
        GroupCreateCommand addAlphaCommand = new GroupCreateCommand(new Group("Alpha"));

        assertTrue(addRaidCommand.equals(addRaidCommand));
        GroupCreateCommand addRaidCommandCopy = new GroupCreateCommand(new Group("Raid Team"));
        assertTrue(addRaidCommand.equals(addRaidCommandCopy));
        assertFalse(addRaidCommand.equals(1));
        assertFalse(addRaidCommand.equals(null));
        assertFalse(addRaidCommand.equals(addAlphaCommand));
    }

    @Test
    public void toStringMethod() {
        Group group = new Group("Raid Team");
        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(group);
        String expected = GroupCreateCommand.class.getCanonicalName() + "{group=" + group + "}";
        assertEquals(expected, groupCreateCommand.toString());
    }
}
