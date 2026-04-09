package seedu.blockbook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.blockbook.commons.core.Config;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.Version;
import seedu.blockbook.commons.exceptions.DataLoadingException;
import seedu.blockbook.commons.util.ConfigUtil;
import seedu.blockbook.commons.util.StringUtil;
import seedu.blockbook.logic.Logic;
import seedu.blockbook.logic.LogicManager;
import seedu.blockbook.logic.commands.CommandResult;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.ModelManager;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.ReadOnlyUserPrefs;
import seedu.blockbook.model.UserPrefs;
import seedu.blockbook.model.util.SampleDataUtil;
import seedu.blockbook.storage.BlockBookStorage;
import seedu.blockbook.storage.JsonBlockBookStorage;
import seedu.blockbook.storage.JsonUserPrefsStorage;
import seedu.blockbook.storage.Storage;
import seedu.blockbook.storage.StorageManager;
import seedu.blockbook.storage.UserPrefsStorage;
import seedu.blockbook.ui.Ui;
import seedu.blockbook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {
    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    protected String storageInitMessage = "Gamer Contacts successfully loaded.";

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BlockBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        BlockBookStorage blockBookStorage = new JsonBlockBookStorage(userPrefs.getBlockBookFilePath());
        storage = new StorageManager(blockBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s block book contacts.json
     * and {@code userPrefs}. <br>
     * or an empty block book data will be used instead if errors occur when reading {@code storage}'s block book data.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getBlockBookFilePath());

        Optional<ReadOnlyBlockBook> blockBookOptional;
        ReadOnlyBlockBook initialData;
        try {
            blockBookOptional = storage.readBlockBook();
            if (!blockBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getBlockBookFilePath()
                        + ".");

                // Shows content on resultDisplay
                CommandResult message = new CommandResult(
                        "No save file found! Starting with an empty Gamer Contact list!", false, false);
                storageInitMessage = message.getFeedbackToUser();
            }
            initialData = blockBookOptional.orElseGet(SampleDataUtil::getSampleBlockBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getBlockBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty Gamer Contact list instead!");

            // Shows content on resultDisplay
            CommandResult message = new CommandResult(
                    "Data file at " + storage.getBlockBookFilePath() + " could not be loaded."
                            + "\nWill be starting with an empty Gamer Contact list instead!", false, false);
            storageInitMessage = message.getFeedbackToUser();

            initialData = new BlockBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        ui = new UiManager(logic, getHostServices());
        logger.info("Starting BlockBook " + MainApp.VERSION);
        ui.start(primaryStage);

        // Placement in start() sure that ui is instantiated before message is shown
        ui.showMessage(storageInitMessage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping BlockBook ] =============================");
        try {
            storage.saveBlockBook(model.getBlockBook());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}

