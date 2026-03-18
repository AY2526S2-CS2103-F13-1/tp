package seedu.blockbook.model;

import java.nio.file.Path;

import seedu.blockbook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getBlockBookFilePath();

}

