package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.gamer.Group;

public class GroupListCommandTest {

    @Test
    public void execute_listGroupsWhenEmpty_returnsEmptyMessage() throws Exception {
        Model model = new ModelManager();

        CommandResult commandResult = new GroupListCommand().execute(model);

        assertEquals(GroupListCommand.MESSAGE_NO_GROUPS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listGroupsWhenPresent_returnsGroupList() throws Exception {
        Model model = new ModelManager();
        Group raidTeam = new Group("Raid Team");
        Group alpha = new Group("Alpha");
        model.addGroup(raidTeam);
        model.addGroup(alpha);

        CommandResult commandResult = new GroupListCommand().execute(model);

        String expectedGroupList = "Raid Team, Alpha";
        String expectedMessage = String.format(GroupListCommand.MESSAGE_SUCCESS, expectedGroupList);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        GroupListCommand firstCommand = new GroupListCommand();
        GroupListCommand secondCommand = new GroupListCommand();

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        GroupListCommand command = new GroupListCommand();
        String expected = GroupListCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, command.toString());
    }
}
