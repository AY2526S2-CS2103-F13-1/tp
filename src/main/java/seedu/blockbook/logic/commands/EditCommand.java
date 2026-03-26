package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.blockbook.commons.core.LogsCenter;
import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.CollectionUtil;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Country;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Favourite;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Group;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Note;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;
import seedu.blockbook.model.gamer.Server;

/**
 * Edits the details of an existing gamer in the BlockBook.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a gamer in BlockBook.\n"
            + "Format: edit INDEX [gamertag/GAMERTAG] [name/NAME] [phone/PHONE] [email/EMAIL] "
            + "[group/GROUP] [server/SERVER] [favourite/FAVOURITE] [country/COUNTRY] [region/REGION] [note/NOTE]\n"
            + "Example: edit 1 name/Herobrine gamertag/ilovesteve phone/99999 "
            + "email/brine@gmail.com group/DestroySteve favourite/fav country/Singapore region/SEA "
            + "note/I hate steve";

    public static final String MESSAGE_EDIT_GAMER_SUCCESS = "Contact edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GAMER = "This gamertag is already used by someone in BlockBook.";
    public static final String MESSAGE_DUPLICATE_GROUP = "This gamer is already in the specified group.";

    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    private final Index index;
    private final EditGamerDescriptor editGamerDescriptor;

    /**
     * @param index               of the gamer in the filtered gamer list to edit
     * @param editGamerDescriptor details to edit the gamer with
     */
    public EditCommand(Index index, EditGamerDescriptor editGamerDescriptor) {
        requireNonNull(index);
        requireNonNull(editGamerDescriptor);
        this.index = index;
        this.editGamerDescriptor = new EditGamerDescriptor(editGamerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Gamer> lastShownList = model.getFilteredGamerList();
        validateEditIndex(lastShownList);

        int targetIndex = index.getZeroBased();
        assert targetIndex < lastShownList.size();

        Gamer gamerToEdit = lastShownList.get(targetIndex);
        assert gamerToEdit != null;

        if (editGamerDescriptor.getGroups().isPresent() && !editGamerDescriptor.getGroups().get().isEmpty()) {
            Set<Group> groupsToAdd = editGamerDescriptor.getGroups().get();
            // If the gamer already has the group(s) the user is trying to add, throw the error
            if (gamerToEdit.getGroups().containsAll(groupsToAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_GROUP);
            }
        }

        Gamer editedGamer = createEditedGamer(gamerToEdit, editGamerDescriptor);
        assert editedGamer != null;

        if (!gamerToEdit.isSameGamer(editedGamer) && model.hasGamer(editedGamer)) {
            throw new CommandException(MESSAGE_DUPLICATE_GAMER);
        }

        Set<Group> existingGroups = model.getBlockBook().getGamerList().stream()
                .flatMap(g -> g.getGroups().stream())
                .collect(Collectors.toSet());

        Set<Group> groupsAddedToGamer = new HashSet<>(editedGamer.getGroups());
        groupsAddedToGamer.removeAll(gamerToEdit.getGroups());

        model.setGamer(gamerToEdit, editedGamer);
        model.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        logger.fine("Edited gamer at index " + index.getOneBased() + ": " + editedGamer.getGamerTag());

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(String.format(MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer)));

        // Check if group already exists
        for (Group group : groupsAddedToGamer) {
            if (!existingGroups.contains(group)) {
                resultMessage.append(String.format("\nGroup %s created. Gamertag: %s added to Group: %s.",
                        group.fullGroup, editedGamer.getGamerTag().fullGamerTag, group.fullGroup));
            } else {
                resultMessage.append(String.format("\nGamertag: %s added to Group: %s.",
                        editedGamer.getGamerTag().fullGamerTag, group.fullGroup));
            }
        }

        if (!gamerToEdit.isSameGamer(editedGamer) && model.hasGamer(editedGamer)) {
            logger.finer("Edit failed: duplicate gamertag at index " + index.getOneBased());
            throw new CommandException(MESSAGE_DUPLICATE_GAMER);
        }


        return new CommandResult(resultMessage.toString());
    }

    /**
     * Validates whether the target index refers to a valid gamer in the displayed list.
     *
     * @param gamerList The currently displayed list of gamers.
     * @throws CommandException If the index is out of range.
     */
    private void validateEditIndex(List<Gamer> gamerList) throws CommandException {
        int targetIndex = index.getZeroBased();
        if (targetIndex >= gamerList.size()) {
            logger.finer("Edit failed: index out of range (" + targetIndex + ").");
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }
    }

    /**
     * Creates and returns a {@code Gamer} with the details of {@code gamerToEdit}
     * edited with {@code editGamerDescriptor}.
     */
    private static Gamer createEditedGamer(Gamer gamerToEdit, EditGamerDescriptor editGamerDescriptor) {
        assert gamerToEdit != null;
        requireNonNull(editGamerDescriptor);

        Name updatedName = editGamerDescriptor.getName().orElse(gamerToEdit.getName());
        GamerTag updatedGamerTag = editGamerDescriptor.getGamerTag().orElse(gamerToEdit.getGamerTag());
        Phone updatedPhone = editGamerDescriptor.getPhone().orElse(gamerToEdit.getPhone());
        Email updatedEmail = editGamerDescriptor.getEmail().orElse(gamerToEdit.getEmail());
        Set<Group> updatedGroups = new HashSet<>(gamerToEdit.getGroups());
        if (editGamerDescriptor.getGroups().isPresent()) {
            Set<Group> parsedGroups = editGamerDescriptor.getGroups().get();
            if (parsedGroups.isEmpty()) {
                updatedGroups.clear(); // User typed "group/" to clear all groups
            } else {
                updatedGroups.addAll(parsedGroups); // User typed "group/Sniper", append to existing
            }
        }
        Server updatedServer = editGamerDescriptor.getServer().orElse(gamerToEdit.getServer());
        Favourite updatedFavourite = editGamerDescriptor.getFavourite().orElse(gamerToEdit.getFavourite());
        Country updatedCountry = editGamerDescriptor.getCountry().orElse(gamerToEdit.getCountry());
        Region updatedRegion = editGamerDescriptor.getRegion().orElse(gamerToEdit.getRegion());
        Note updatedNote = editGamerDescriptor.getNote().orElse(gamerToEdit.getNote());

        return new Gamer(
                updatedName,
                updatedGamerTag,
                updatedPhone,
                updatedEmail,
                updatedGroups,
                updatedServer,
                updatedFavourite,
                updatedCountry,
                updatedRegion,
                updatedNote
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editGamerDescriptor.equals(otherEditCommand.editGamerDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editGamerDescriptor", editGamerDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the gamer with. Each non-empty field value will replace the
     * corresponding field value of the gamer.
     */
    public static class EditGamerDescriptor {
        private Name name;
        private GamerTag gamerTag;
        private Phone phone;
        private Email email;
        private Set<Group> groups;
        private Server server;
        private Favourite favourite;
        private Country country;
        private Region region;
        private Note note;

        public EditGamerDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditGamerDescriptor(EditGamerDescriptor toCopy) {
            setName(toCopy.name);
            setGamerTag(toCopy.gamerTag);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setGroups(toCopy.groups);
            setServer(toCopy.server);
            setFavourite(toCopy.favourite);
            setCountry(toCopy.country);
            setRegion(toCopy.region);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    name, gamerTag, phone, email, groups, server, favourite, country, region, note);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGamerTag(GamerTag gamerTag) {
            this.gamerTag = gamerTag;
        }

        public Optional<GamerTag> getGamerTag() {
            return Optional.ofNullable(gamerTag);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setGroups(Set<Group> groups) {
            this.groups = (groups != null) ? new HashSet<>(groups) : null;
        }

        public Optional<Set<Group>> getGroups() {
            return (groups != null) ? Optional.of(Collections.unmodifiableSet(groups)) : Optional.empty();
        }

        public void setServer(Server server) {
            this.server = server;
        }

        public Optional<Server> getServer() {
            return Optional.ofNullable(server);
        }

        public void setFavourite(Favourite favourite) {
            this.favourite = favourite;
        }

        public Optional<Favourite> getFavourite() {
            return Optional.ofNullable(favourite);
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public Optional<Region> getRegion() {
            return Optional.ofNullable(region);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditGamerDescriptor)) {
                return false;
            }

            EditGamerDescriptor otherDescriptor = (EditGamerDescriptor) other;
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(gamerTag, otherDescriptor.gamerTag)
                    && Objects.equals(phone, otherDescriptor.phone)
                    && Objects.equals(email, otherDescriptor.email)
                    && Objects.equals(groups, otherDescriptor.groups)
                    && Objects.equals(server, otherDescriptor.server)
                    && Objects.equals(favourite, otherDescriptor.favourite)
                    && Objects.equals(country, otherDescriptor.country)
                    && Objects.equals(region, otherDescriptor.region)
                    && Objects.equals(note, otherDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("gamerTag", gamerTag)
                    .add("phone", phone)
                    .add("email", email)
                    .add("groups", groups)
                    .add("server", server)
                    .add("favourite", favourite)
                    .add("country", country)
                    .add("region", region)
                    .add("note", note)
                    .toString();
        }
    }
}
