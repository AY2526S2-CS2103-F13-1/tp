package seedu.blockbook.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.commands.Command;
import seedu.blockbook.logic.commands.CommandResult;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.logic.parser.BlockBookParser;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final BlockBookParser blockBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        blockBookParser = new BlockBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = blockBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveBlockBook(model.getBlockBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBlockBook getBlockBook() {
        return model.getBlockBook();
    }

    @Override
    public ObservableList<Gamer> getFilteredGamerList() {
        return model.getFilteredGamerList();
    }

    @Override
    public Path getBlockBookFilePath() {
        return model.getBlockBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}


