package seedu.blockbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.blockbook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.blockbook.testutil.Assert.assertThrows;
import static seedu.blockbook.testutil.TypicalIndexes.INDEX_FIRST_GAMER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.blockbook.logic.commands.AddCommand;
import seedu.blockbook.logic.commands.ClearCommand;
import seedu.blockbook.logic.commands.DeleteCommand;
import seedu.blockbook.logic.commands.EditCommand;
import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
import seedu.blockbook.logic.commands.ExitCommand;
import seedu.blockbook.logic.commands.FindCommand;
import seedu.blockbook.logic.commands.GroupCreateCommand;
import seedu.blockbook.logic.commands.GroupListCommand;
import seedu.blockbook.logic.commands.HelpCommand;
import seedu.blockbook.logic.commands.ListCommand;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.AnyAttributeContainsKeywordsPredicate;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.testutil.EditGamerDescriptorBuilder;
import seedu.blockbook.testutil.GamerBuilder;
import seedu.blockbook.testutil.GamerUtil;

public class BlockBookParserTest {

    private final BlockBookParser parser = new BlockBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Gamer gamer = new GamerBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(GamerUtil.getAddCommand(gamer));
        assertEquals(new AddCommand(GamerUtil.getAddCommandGamer(gamer)), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_GAMER.getOneBased());
        assertEquals(new DeleteCommand(Arrays.asList(INDEX_FIRST_GAMER)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Gamer gamer = new GamerBuilder().build();
        EditGamerDescriptor descriptor = new EditGamerDescriptorBuilder(gamer).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GAMER.getOneBased() + " " + GamerUtil.getEditGamerDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_GAMER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String keywordInput = keywords.stream().collect(Collectors.joining(" "));
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywordInput);
        assertEquals(new FindCommand(new AnyAttributeContainsKeywordsPredicate(keywordInput)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_groupCreate_success() throws Exception {
        Group group = new Group("ilove Steve");
        GroupCreateCommand expectedCommand = new GroupCreateCommand(group);
        assertEquals(expectedCommand, parser.parseCommand("groupcreate ilove Steve"));
    }

    @Test
    public void parseCommand_groupCreateAlias_success() throws Exception {
        Group group = new Group("Alpha");
        GroupCreateCommand expectedCommand = new GroupCreateCommand(group);
        assertEquals(expectedCommand, parser.parseCommand("gc Alpha"));
    }

    @Test
    public void parseCommand_groupList_success() throws Exception {
        assertTrue(parser.parseCommand("grouplist") instanceof GroupListCommand);
    }

    @Test
    public void parseCommand_groupListAlias_success() throws Exception {
        assertTrue(parser.parseCommand("gl") instanceof GroupListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                        -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
