package seedu.blockbook.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.blockbook.logic.commands.EditCommand.EditGamerDescriptor;
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
 * A utility class to help with building EditGamerDescriptor objects.
 */
public class EditGamerDescriptorBuilder {

    private final EditGamerDescriptor descriptor;

    public EditGamerDescriptorBuilder() {
        descriptor = new EditGamerDescriptor();
    }

    public EditGamerDescriptorBuilder(EditGamerDescriptor descriptor) {
        this.descriptor = new EditGamerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGamerDescriptor} with fields containing {@code gamer}'s details.
     */
    public EditGamerDescriptorBuilder(Gamer gamer) {
        descriptor = new EditGamerDescriptor();
        descriptor.setName(gamer.getName());
        descriptor.setGamerTag(gamer.getGamerTag());
        descriptor.setPhone(gamer.getPhone());
        descriptor.setEmail(gamer.getEmail());
        descriptor.setGroups(gamer.getGroups());
        descriptor.setServer(gamer.getServer());
        descriptor.setFavourite(gamer.getFavourite());
        descriptor.setCountry(gamer.getCountry());
        descriptor.setRegion(gamer.getRegion());
        descriptor.setNote(gamer.getNote());
    }

    /**
     * Sets the {@code Name} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code GamerTag} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withGamerTag(String gamerTag) {
        descriptor.setGamerTag(new GamerTag(gamerTag));
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

    /**
     * Parses the {@code groups} into a {@code Set<Group>} and set it to the {@code EditGamerDescriptor}
     * that we are building.
     */
    public EditGamerDescriptorBuilder withGroups(String... groups) {
        Set<Group> groupSet = Stream.of(groups).map(Group::new).collect(Collectors.toSet());
        descriptor.setGroups(groupSet);
        return this;
    }

    /**
     * Sets the {@code Server} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withServer(String server) {
        descriptor.setServer(new Server(server));
        return this;
    }

    /**
     * Sets the {@code Favourite} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withFavourite(String favourite) {
        descriptor.setFavourite(new Favourite(favourite));
        return this;
    }

    /**
     * Sets the {@code Country} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withCountry(String country) {
        descriptor.setCountry(new Country(country));
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withRegion(String region) {
        descriptor.setRegion(new Region(region));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditGamerDescriptor} that we are building.
     */
    public EditGamerDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    public EditGamerDescriptor build() {
        return descriptor;
    }
}
