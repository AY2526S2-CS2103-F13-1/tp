package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.FavouriteStatus;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Marks or unmarks a gamer as favourite using its displayed index from the BlockBook.
 */
public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_WORD_UNFAVOURITE = "unfavourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the favourite status of the gamer identified by the index number used in the displayed "
            + "gamer list.\n"
            + "Format: " + COMMAND_WORD + " INDEX\n"
            + "Example: " + COMMAND_WORD + " 2\n"
            + COMMAND_WORD_UNFAVOURITE + ": Removes a gamer from favourites using the displayed index.\n"
            + "Format: " + COMMAND_WORD_UNFAVOURITE + " INDEX\n"
            + "Example: " + COMMAND_WORD_UNFAVOURITE + " 2";

    public static final String MESSAGE_MARK_FAVOURITE_SUCCESS =
            "Contact updated as favourite: %1$s";
    public static final String MESSAGE_UNMARK_FAVOURITE_SUCCESS =
            "Contact removed from favourites: %1$s";
    public static final String MESSAGE_ALREADY_FAVOURITE =
            "You have already favourite the gamer: %1$s";
    public static final String MESSAGE_ALREADY_UNFAVOURITE =
            "This gamer is already not a favourite: %1$s";
    public static final String MESSAGE_EMPTY_CONTACT_LIST = "No contacts to favourite. The list is empty";

    private static final Logger logger = LogsCenter.getLogger(FavouriteCommand.class);

    private final Index targetIndex;
    private final boolean markFavourite;

    /**
     * Creates a FavouriteCommand to mark or unmark the favourite status of the gamer at {@code targetIndex}.
     *
     * @param targetIndex Index of the gamer in the filtered gamer list.
     * @param markFavourite True to mark as favourite, false to unmark.
     */
    public FavouriteCommand(Index targetIndex, boolean markFavourite) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.markFavourite = markFavourite;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Gamer> lastShownList = model.getFilteredGamerList();
        validateFavouriteIndex(lastShownList);

        int index = targetIndex.getZeroBased();
        assert index < lastShownList.size();

        Gamer gamerToToggle = lastShownList.get(index);
        assert gamerToToggle != null;

        String name = Messages.formatNullable(gamerToToggle.getName());
        boolean isCurrentlyFavourite = isCurrentlyFavourite(gamerToToggle);
        if (markFavourite && isCurrentlyFavourite) {
            logger.fine("Favourite failed: already favourite (" + name + ").");
            throw new CommandException(String.format(MESSAGE_ALREADY_FAVOURITE, name));
        }
        if (!markFavourite && !isCurrentlyFavourite) {
            logger.fine("Unfavourite failed: already unfavourite (" + name + ").");
            throw new CommandException(String.format(MESSAGE_ALREADY_UNFAVOURITE, name));
        }

        FavouriteStatus updatedStatus = markFavourite ? FavouriteStatus.FAV : FavouriteStatus.UNFAV;
        Favourite updatedFavourite = Favourite.fromStatus(updatedStatus);
        assert updatedFavourite != null;

        Gamer updatedGamer = createToggledGamer(gamerToToggle, updatedFavourite);
        model.setGamer(gamerToToggle, updatedGamer);

        if (!markFavourite) {
            String summary = formatContactSummaryForUnmark(updatedGamer);
            logger.fine("Unmarked favourite: " + summary);
            return new CommandResult(String.format(MESSAGE_UNMARK_FAVOURITE_SUCCESS, summary));
        }

        String summary = formatContactSummaryForMark(updatedGamer);
        logger.fine("Marked favourite: " + summary);
        return new CommandResult(String.format(MESSAGE_MARK_FAVOURITE_SUCCESS, summary));
    }

    /**
     * Validates whether the target index refers to a valid gamer in the given list.
     *
     * @param gamerList The currently displayed list of gamers.
     * @throws CommandException If the list is empty or if the index is out of range.
     */
    private void validateFavouriteIndex(List<Gamer> gamerList) throws CommandException {
        if (gamerList.isEmpty()) {
            logger.finer("Favourite failed: list is empty.");
            throw new CommandException(MESSAGE_EMPTY_CONTACT_LIST);
        }

        int index = targetIndex.getZeroBased();
        if (index >= gamerList.size()) {
            logger.finer("Favourite failed: index out of range (" + index + ").");
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
    }

    private static boolean isCurrentlyFavourite(Gamer gamer) {
        Favourite favourite = gamer.getFavourite();
        return favourite != null && favourite.isFav();
    }

    private static Gamer createToggledGamer(Gamer gamerToEdit, Favourite updatedFavourite) {
        requireNonNull(gamerToEdit);
        requireNonNull(updatedFavourite);

        return new Gamer(
                gamerToEdit.getName(),
                gamerToEdit.getGamerTag(),
                gamerToEdit.getPhone(),
                gamerToEdit.getEmail(),
                gamerToEdit.getGroups(),
                gamerToEdit.getServer(),
                updatedFavourite,
                gamerToEdit.getCountry(),
                gamerToEdit.getRegion(),
                gamerToEdit.getNote()
        );
    }

    private static String formatContactSummaryForMark(Gamer gamer) {
        return String.format("Name: %s GamerTag: %s Favourite: %s",
                Messages.formatNullable(gamer.getName()),
                Messages.formatNullable(gamer.getGamerTag()),
                Messages.formatNullable(gamer.getFavourite()));
    }

    private static String formatContactSummaryForUnmark(Gamer gamer) {
        return String.format("Name: %s GamerTag: %s Favourite: %s",
                Messages.formatNullable(gamer.getName()),
                Messages.formatNullable(gamer.getGamerTag()),
                Messages.formatNullable(gamer.getFavourite()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        FavouriteCommand otherFavouriteCommand = (FavouriteCommand) other;
        return targetIndex.equals(otherFavouriteCommand.targetIndex)
                && markFavourite == otherFavouriteCommand.markFavourite;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("markFavourite", markFavourite)
                .toString();
    }
}
