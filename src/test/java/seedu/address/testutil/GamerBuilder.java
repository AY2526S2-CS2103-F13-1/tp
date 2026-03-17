package seedu.address.testutil;

//import java.util.HashSet;
//import java.util.Set;

import seedu.address.model.gamer.Gamer;
import seedu.address.model.gamer.GamerTag;
import seedu.address.model.gamer.Name;

//import seedu.address.model.gamer.Phone;
//import seedu.address.model.gamer.Email;
//import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class GamerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_GAMER_TAG = "Herobrine";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private GamerTag gamerTag;
    // private Phone phone;
    // private Email email;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public GamerBuilder() {
        name = new Name(DEFAULT_NAME);
        gamerTag = new GamerTag(DEFAULT_GAMER_TAG);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code gamerToCopy}.
     */
    public GamerBuilder(Gamer gamerToCopy) {
        name = gamerToCopy.getName();
        gamerTag = gamerToCopy.getGamerTag();
        // email = gamerToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GamerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code GamerTag} of the {@code Gamer} that we are building.
     */
    public GamerBuilder withGamerTag(String gamerTag) {
        this.gamerTag = new GamerTag(gamerTag);
        return this;
    }

    // /**
    // * Sets the {@code Phone} of the {@code Person} that we are building.
    // */
    // public GamerBuilder withPhone(String phone) {
    // this.phone = new Phone(phone);
    // return this;
    // }

    // /**
    // * Sets the {@code Email} of the {@code Person} that we are building.
    // */
    // public GamerBuilder withEmail(String email) {
    // this.email = new Email(email);
    // return this;
    // }

    public Gamer build() {
        return new Gamer(name, gamerTag);
    }

}
