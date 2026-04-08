package seedu.blockbook.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 * A utility class to help with building Gamer objects.
 */
public class GamerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_GAMER_TAG = "Herobrine";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_GROUP = "Raid Team";
    public static final String DEFAULT_SERVER = "127.0.0.1:8080";
    public static final Boolean DEFAULT_FAVOURITE = true;
    public static final String DEFAULT_COUNTRY = "Singapore";
    public static final String DEFAULT_REGION = "ASIA";
    public static final String DEFAULT_NOTE = "test_note";

    private Name name;
    private GamerTag gamerTag;
    private Phone phone;
    private Email email;
    private List<Group> groups;
    private Server server;
    private Favourite favourite;
    private Country country;
    private Region region;
    private Note note;

    /**
     * Creates a {@code GamerBuilder} with the default details.
     */
    public GamerBuilder() {
        name = new Name(DEFAULT_NAME);
        gamerTag = new GamerTag(DEFAULT_GAMER_TAG);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        groups = new ArrayList<>(List.of(new Group(DEFAULT_GROUP)));
        server = new Server(DEFAULT_SERVER);
        favourite = new Favourite(DEFAULT_FAVOURITE);
        country = new Country(DEFAULT_COUNTRY);
        region = new Region(DEFAULT_REGION);
        note = new Note(DEFAULT_NOTE);
    }

    /**
     * Initializes the GamerBuilder with the data of {@code gamerToCopy}.
     */
    public GamerBuilder(Gamer gamerToCopy) {
        name = gamerToCopy.getName();
        gamerTag = gamerToCopy.getGamerTag();
        phone = gamerToCopy.getPhone();
        email = gamerToCopy.getEmail();
        groups = new ArrayList<>(gamerToCopy.getGroups());
        server = gamerToCopy.getServer();
        favourite = gamerToCopy.getFavourite();
        country = gamerToCopy.getCountry();
        region = gamerToCopy.getRegion();
        note = gamerToCopy.getNote();
    }

    /**
     * Sets the {@code Name} of the {@code Gamer} that we are building.
     *
     * @param name A valid name.
     * @return This builder.
     */
    public GamerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code GamerTag} of the {@code Gamer} that we are building.
     *
     * @param gamerTag A valid gamertag.
     * @return This builder.
     */
    public GamerBuilder withGamerTag(String gamerTag) {
        this.gamerTag = new GamerTag(gamerTag);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Gamer} that we are building.
     *
     * @param phone A valid phone number.
     * @return This builder.
     */
    public GamerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Gamer} that we are building.
     *
     * @param email A valid email.
     * @return This builder.
     */
    public GamerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Groups} of the {@code Gamer} that we are building.
     *
     * @param groups Valid group names.
     * @return This builder.
     */
    public GamerBuilder withGroups(String... groups) {
        this.groups = Arrays.stream(groups)
                .map(Group::new)
                .collect(Collectors.toList());
        return this;
    }

    /**
     * Sets the {@code Server} of the {@code Gamer} that we are building.
     *
     * @param server A valid server.
     * @return This builder.
     */
    public GamerBuilder withServer(String server) {
        this.server = new Server(server);
        return this;
    }

    /**
     * Sets the {@code Favourite} of the {@code Gamer} that we are building.
     *
     * @param favourite A valid favourite status.
     * @return This builder.
     */
    public GamerBuilder withFavourite(Boolean favourite) {
        this.favourite = new Favourite(favourite);
        return this;
    }

    /**
     * Sets the {@code Country} of the {@code Gamer} that we are building.
     *
     * @param country A valid country.
     * @return This builder.
     */
    public GamerBuilder withCountry(String country) {
        this.country = new Country(country);
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code Gamer} that we are building.
     *
     * @param region A valid region.
     * @return This builder.
     */
    public GamerBuilder withRegion(String region) {
        this.region = new Region(region);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Gamer} that we are building.
     *
     * @param note A valid note.
     * @return This builder.
     */
    public GamerBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Builds and returns the {@code Gamer} with the configured fields.
     */
    public Gamer build() {
        return new Gamer(name, gamerTag, phone, email, groups, server, favourite, country, region, note);
    }
}
