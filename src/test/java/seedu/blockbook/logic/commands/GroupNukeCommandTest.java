package seedu.blockbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.blockbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.blockbook.testutil.TypicalGamers.getTypicalBlockBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

public class GroupNukeCommandTest {

    @Test
    public void execute_nukeGroup_success() throws CommandException {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index groupIndex = Index.fromOneBased(1);
        Group groupToRemove = model.getGroupList().get(groupIndex.getZeroBased());
        GroupNukeCommand command = new GroupNukeCommand(groupIndex, "");

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        List<String> affectedGamertags = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            if (gamer.getGroups().stream().anyMatch(group -> group.equals(groupToRemove))) {
                affectedGamertags.add(gamer.getGamerTag().toString());
            }
        }
        String affectedList = affectedGamertags.isEmpty() ? "None" : String.join(", ", affectedGamertags);
        String confirmationCode = extractConfirmationCode(exception, groupIndex, groupToRemove, affectedList);
        Model expectedModel = new ModelManager(model.getBlockBook(), new UserPrefs());

        List<Gamer> gamers = new ArrayList<>(expectedModel.getBlockBook().getGamerList());
        for (Gamer gamer : gamers) {
            List<Group> updatedGroups = new ArrayList<>(gamer.getGroups());
            boolean removed = updatedGroups.removeIf(group -> group.equals(groupToRemove));
            if (removed) {
                Gamer updatedGamer = new Gamer(
                        gamer.getName(),
                        gamer.getGamerTag(),
                        gamer.getPhone(),
                        gamer.getEmail(),
                        updatedGroups,
                        gamer.getServer(),
                        gamer.getFavourite(),
                        gamer.getCountry(),
                        gamer.getRegion(),
                        gamer.getNote()
                );
                expectedModel.setGamer(gamer, updatedGamer);
            }
        }
        expectedModel.removeGroup(groupToRemove);

        List<String> removedGamertags = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            if (gamer.getGroups().stream().anyMatch(group -> group.equals(groupToRemove))) {
                removedGamertags.add(gamer.getGamerTag().toString());
            }
        }
        String removedList = removedGamertags.isEmpty() ? "None" : String.join(", ", removedGamertags);
        String expectedMessage = String.format(GroupNukeCommand.MESSAGE_SUCCESS, groupToRemove, removedList);
        assertCommandSuccess(new GroupNukeCommand(groupIndex, confirmationCode),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index outOfBounds = Index.fromOneBased(model.getGroupList().size() + 1);

        GroupNukeCommand command = new GroupNukeCommand(outOfBounds, "");
        assertCommandFailure(command, model, Messages.MESSAGE_INDEX_OUT_OF_RANGE);
    }

    @Test
    public void execute_wrongConfirmation_throwsConfirmationMessage() {
        Model model = new ModelManager(getTypicalBlockBook(), new UserPrefs());
        Index groupIndex = Index.fromOneBased(1);

        CommandException firstException = assertThrows(
                CommandException.class, () -> new GroupNukeCommand(groupIndex, "").execute(model)
        );
        Group groupToRemove = model.getGroupList().get(groupIndex.getZeroBased());
        List<String> affectedGamertags = new ArrayList<>();
        for (Gamer gamer : model.getBlockBook().getGamerList()) {
            if (gamer.getGroups().stream().anyMatch(group -> group.equals(groupToRemove))) {
                affectedGamertags.add(gamer.getGamerTag().toString());
            }
        }
        String affectedList = affectedGamertags.isEmpty() ? "None" : String.join(", ", affectedGamertags);
        String generatedCode = extractConfirmationCode(firstException, groupIndex, groupToRemove, affectedList);
        String wrongCode = generatedCode.equals(" aaaaaa") ? " bbbbbb" : " aaaaaa";
        assertNotEquals(generatedCode, wrongCode);

        CommandException secondException = assertThrows(CommandException.class, () ->
                new GroupNukeCommand(groupIndex, wrongCode).execute(model)
        );
        extractConfirmationCode(secondException, groupIndex, groupToRemove, affectedList);
    }

    private String extractConfirmationCode(CommandException exception, Index groupIndex,
                                           Group groupToRemove, String affectedList) {
        String message = exception.getMessage();
        String prefix = String.format(GroupNukeCommand.CONFIRMATION_MESSAGE_PREFIX,
                groupToRemove, affectedList)
                + groupIndex.getOneBased();
        assertTrue(message.startsWith(prefix));

        String confirmationCode = message.substring(prefix.length());
        assertTrue(confirmationCode.matches(" [a-z0-9]{6}"));
        return confirmationCode;
    }
}
