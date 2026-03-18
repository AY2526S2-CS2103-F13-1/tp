package seedu.blockbook.testutil;

//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
//import seedu.blockbook.model.gamer.Address;
import seedu.blockbook.model.gamer.Email;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Name;
import seedu.blockbook.model.gamer.Phone;

//import seedu.blockbook.model.tag.Tag;

/**
 * A utility class to help with building EditGamerDescriptor objects.
 */
public class EditGamerDescriptorBuilder {

    private EditGamerDescriptor descriptor;

    public EditGamerDescriptorBuilder() {
        descriptor = new EditGamerDescriptor();
    }

    public EditGamerDescriptorBuilder(EditGamerDescriptor descriptor) {
        this.descriptor = new EditGamerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGamerDescriptor} with fields containing {@code gamer}'s details
     */
    public EditGamerDescriptorBuilder(Gamer gamer) {
        descriptor = new EditGamerDescriptor();
        descriptor.setName(gamer.getName());
        // descriptor.setPhone(gamer.getPhone());
        // descriptor.setEmail(gamer.getEmail());
    }

    /**
     * Sets the {@code Name} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }


    public EditGamerDescriptor build() {
        return descriptor;
    }
}

