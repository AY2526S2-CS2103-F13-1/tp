package seedu.blockbook.ui;

import java.util.logging.Logger;

import javafx.application.HostServices;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.blockbook.commons.core.GuiSettings;
import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.logic.Logic;
import seedu.blockbook.logic.commands.CommandResult;
import seedu.blockbook.logic.commands.DeleteCommand;
import seedu.blockbook.logic.commands.FindCommand;
import seedu.blockbook.logic.commands.GroupViewCommand;
import seedu.blockbook.logic.commands.ListCommand;
import seedu.blockbook.logic.commands.SortCommand;
import seedu.blockbook.logic.commands.ViewCommand;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.logic.parser.exceptions.ParseException;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String HOMEPAGE_URL = "https://ay2526s2-cs2103-f13-1.github.io/tp";
    private static final String USERGUIDE_URL = HOMEPAGE_URL + "/UserGuide.html";
    private static final String DEVELOPERGUIDE_URL = HOMEPAGE_URL + "/DeveloperGuide.html";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private HostServices hostServices;

    // Independent Ui parts residing in this Ui container
    private GamerListPanel gamerListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ViewWindow viewWindow;
    private Gamer viewedGamer;
    private GroupListPanel groupListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private Menu exitMenu;

    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private MenuItem websiteMenuItem;
    @FXML
    private MenuItem userGuideMenuItem;
    @FXML
    private MenuItem developerGuideMenuItem;

    @FXML
    private StackPane gamerListPanelPlaceholder;

    @FXML
    private StackPane groupListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic, HostServices hostServices) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.hostServices = hostServices;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        configureExitMenu();

        setAccelerators();

        helpWindow = new HelpWindow();
        viewWindow = new ViewWindow();
        registerGamerListListener();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(exitMenu, KeyCombination.valueOf("Shortcut+Q"));
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(websiteMenuItem, KeyCombination.valueOf("F2"));
        setAccelerator(userGuideMenuItem, KeyCombination.valueOf("F3"));
        setAccelerator(developerGuideMenuItem, KeyCombination.valueOf("F4"));
    }

    /**
     * Ensures clicking the top-level "Exit" menu closes the app in one click.
     */
    private void configureExitMenu() {
        exitMenu.setOnAction(event -> handleExit());
        exitMenu.setOnShowing(event -> handleExit());
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        gamerListPanel = new GamerListPanel(logic.getFilteredGamerList(), this::handleViewOnGamer);
        gamerListPanelPlaceholder.getChildren().add(gamerListPanel.getRoot());

        groupListPanel = new GroupListPanel(logic.getGroupList(), this::handleGroupView);
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getBlockBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the BlockBook Main Website.
     */
    @FXML
    public void openWebsite() {
        hostServices.showDocument(HOMEPAGE_URL);
    }

    /**
     * Opens the BlockBook User Guide.
     */
    @FXML
    public void openUserGuide() {
        hostServices.showDocument(USERGUIDE_URL);
    }

    /**
     * Opens the BlockBook Developer Guide.
     */
    @FXML
    public void openDeveloperGuide() {
        hostServices.showDocument(DEVELOPERGUIDE_URL);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        viewWindow.hide();
        primaryStage.hide();
    }

    /**
     * Opens the help menu.
     */
    @FXML
    private void handleHelp() throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute("help");
            helpWindow.setHelpText(commandResult.getFeedbackToUser());
            if (!helpWindow.isShowing()) {
                helpWindow.show();
            } else {
                helpWindow.focus();
            }
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing help command.");
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Opens the view popup with the specified gamer details.
     */
    private void handleViewPopUp(CommandResult commandResult) {
        logic.getViewedGamer().ifPresent(gamer -> {
            viewWindow.setGamer(gamer, getDisplayedIndex(gamer));
            viewedGamer = gamer;
            if (!viewWindow.isShowing()) {
                viewWindow.show();
            } else {
                viewWindow.focus();
            }
        });
    }

    /**
     * Keeps the view popup in sync with list edits and deletions.
     */
    private void registerGamerListListener() {
        logic.getBlockBook().getGamerList().addListener((ListChangeListener<Gamer>) change -> {
            if (viewedGamer == null) {
                return;
            }

            while (change.next()) {
                if (change.wasRemoved() || change.wasReplaced()) {
                    boolean removedViewed = change.getRemoved().stream().anyMatch(viewedGamer::equals);
                    if (!removedViewed) {
                        continue;
                    }

                    if (change.wasReplaced() && change.getAddedSize() == 1) {
                        Gamer updated = change.getAddedSubList().get(0);
                        viewedGamer = updated;
                        if (viewWindow.isShowing()) {
                            viewWindow.setGamer(updated, getDisplayedIndex(updated));
                        }
                    } else {
                        viewedGamer = null;
                        viewWindow.hide();
                    }
                }
            }
        });
    }

    public GamerListPanel getGamerListPanel() {
        return gamerListPanel;
    }

    private int getDisplayedIndex(Gamer gamer) {
        int filteredIndex = logic.getFilteredGamerList().indexOf(gamer);
        if (filteredIndex >= 0) {
            return filteredIndex + 1;
        }
        int fullIndex = logic.getBlockBook().getGamerList().indexOf(gamer);
        return fullIndex >= 0 ? fullIndex + 1 : 1;
    }

    private void handleViewOnGamer(Gamer gamer) {
        int index = logic.getFilteredGamerList().indexOf(gamer);
        if (index < 0) {
            return;
        }
        String commandText = ViewCommand.COMMAND_WORD + " " + (index + 1);
        try {
            executeCommand(commandText);
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing view command from list double-click.");
        }
    }

    private void handleGroupView(Group group) {
        if (group == null) {
            return;
        }
        int index = logic.getGroupList().indexOf(group);
        if (index < 0) {
            return;
        }
        String commandText = GroupViewCommand.COMMAND_WORD + " " + (index + 1);
        try {
            executeCommand(commandText);
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing groupview command from group card double-click.");
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.blockbook.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowView()) {
                handleViewPopUp(commandResult);
            }

            if (isFindOrSortCommand(commandText)) {
                viewedGamer = null;
                viewWindow.hide();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    public void showMessage(String text) { // Ensure this is public
        resultDisplay.setFeedbackToUser(text);
    }

    private boolean isFindOrSortCommand(String commandText) {
        String trimmed = commandText.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        String commandWord = trimmed.split("\\s+")[0];
        return FindCommand.COMMAND_WORD.equals(commandWord)
                || FindCommand.COMMAND_ALIAS.equals(commandWord)
                || ListCommand.COMMAND_WORD.equals(commandWord)
                || ListCommand.COMMAND_ALIAS.equals(commandWord)
                || SortCommand.COMMAND_WORD.equals(commandWord)
                || SortCommand.COMMAND_ALIAS.equals(commandWord)
                || DeleteCommand.COMMAND_WORD.equals(commandWord)
                || DeleteCommand.COMMAND_ALIAS.equals(commandWord);
    }
}
