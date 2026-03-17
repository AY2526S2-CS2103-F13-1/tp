package seedu.address.testutil;

//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditGamerDescriptor;
//import seedu.address.model.gamer.Address;
import seedu.address.model.gamer.Email;
import seedu.address.model.gamer.Gamer;
import seedu.address.model.gamer.Name;
import seedu.address.model.gamer.Phone;

//import seedu.address.model.tag.Tag;

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
     * Returns an {@code EditGamerDescriptor} with fields containing {@code person}'s details
     */
    public EditGamerDescriptorBuilder(Gamer person) {
        descriptor = new EditGamerDescriptor();
        descriptor.setName(person.getName());
        // descriptor.setPhone(person.getPhone());
        // descriptor.setEmail(person.getEmail());
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
