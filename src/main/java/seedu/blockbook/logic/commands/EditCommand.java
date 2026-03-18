package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.blockbook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.blockbook.model.Model.PREDICATE_SHOW_ALL_GAMERS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.blockbook.commons.core.index.Index;
import seedu.blockbook.commons.util.CollectionUtil;
import seedu.blockbook.commons.util.ToStringBuilder;
import seedu.blockbook.logic.Messages;
import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.Model;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.GamerTag;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Phone;
import seedu.blockbook.model.gamer.Region;

/**
 * Edits the details of an existing gamer in the BlockBook.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the gamer identified "
            + "by the index number used in the displayed gamer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_GAMER_SUCCESS = "Edited Gamer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GAMER = "This gamer already exists in the BlockBook.";

    private final Index index;
    private final EditGamerDescriptor editGamerDescriptor;

    /**
     * @param index of the gamer in the filtered gamer list to edit
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Gamer gamerToEdit = lastShownList.get(index.getZeroBased());
        Gamer editedGamer = createEditedGamer(gamerToEdit, editGamerDescriptor);

        if (!gamerToEdit.isSameGamer(editedGamer) && model.hasGamer(editedGamer)) {
            throw new CommandException(MESSAGE_DUPLICATE_GAMER);
        }

        model.setGamer(gamerToEdit, editedGamer);
        model.updateFilteredGamerList(PREDICATE_SHOW_ALL_GAMERS);
        return new CommandResult(String.format(MESSAGE_EDIT_GAMER_SUCCESS, Messages.format(editedGamer)));
    }

    /**
     * Creates and returns a {@code Gamer} with the details of {@code gamerToEdit}
     * edited with {@code editGamerDescriptor}.
     */
    private static Gamer createEditedGamer(Gamer gamerToEdit, EditGamerDescriptor editGamerDescriptor) {
        assert gamerToEdit != null;

        Name updatedName = editGamerDescriptor.getName().orElse(gamerToEdit.getName());
        // Phone updatedPhone = editGamerDescriptor.getPhone().orElse(gamerToEdit.getPhone());
        // Email updatedEmail = editGamerDescriptor.getEmail().orElse(gamerToEdit.getEmail());
        // Address updatedAddress = editGamerDescriptor.getAddress().orElse(gamerToEdit.getAddress());
        // Set<Tag> updatedTags = editGamerDescriptor.getTags().orElse(gamerToEdit.getTags());

        Region updatedRegion = new Region("Singapore");
        GamerTag updatedGamerTag = new GamerTag("Herobrine");

        return new Gamer(updatedName, updatedGamerTag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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
        private Phone phone;
        private Email email;

        public EditGamerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGamerDescriptor(EditGamerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
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


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGamerDescriptor)) {
                return false;
            }

            EditGamerDescriptor otherEditGamerDescriptor = (EditGamerDescriptor) other;
            return Objects.equals(name, otherEditGamerDescriptor.name)
                    && Objects.equals(phone, otherEditGamerDescriptor.phone)
                    && Objects.equals(email, otherEditGamerDescriptor.email);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .toString();
        }
    }
}


