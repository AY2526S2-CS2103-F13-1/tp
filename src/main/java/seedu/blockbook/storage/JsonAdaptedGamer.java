package seedu.blockbook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.blockbook.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Gamer}.
 */
class JsonAdaptedGamer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Gamer's %s field is missing!";

    private final String name;
    private final String gamerTag;
    private final String phone;
    private final String email;
    private final String group;
    private final String server;
    private final Boolean favourite;
    private final String country;
    private final String region;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedGamer} with the given gamer details.
     */
    @JsonCreator
    public JsonAdaptedGamer(@JsonProperty("name") String name,
                            @JsonProperty("gamerTag") String gamerTag,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("group") String group,
                            @JsonProperty("server") String server,
                            @JsonProperty("favourite") Boolean favourite,
                            @JsonProperty("country") String country,
                            @JsonProperty("region") String region,
                            @JsonProperty("note") String note) {
        this.name = name;
        this.gamerTag = gamerTag;
        this.phone = phone;
        this.email = email;
        this.group = group;
        this.server = server;
        this.favourite = favourite;
        this.country = country;
        this.region = region;
        this.note = note;
    }

    /**
     * Converts a given {@code Gamer} into this class for Jackson use.
     */
    public JsonAdaptedGamer(Gamer source) {
        name = source.getName() != null ? source.getName().fullName : null;
        gamerTag = source.getGamerTag() != null ? source.getGamerTag().fullGamerTag : null;
        phone = source.getPhone() != null ? source.getPhone().fullPhone : null;
        email = source.getEmail() != null ? source.getEmail().fullEmail : null;
        group = source.getGroup() != null ? source.getGroup().fullGroup : null;
        server = source.getServer() != null ? source.getServer().fullServer : null;
        favourite = source.getFavourite() != null ? source.getFavourite().isFav() : false;
        country = source.getCountry() != null ? source.getCountry().fullCountry : null;
        region = source.getRegion() != null ? source.getRegion().fullRegion : null;
        note = source.getNote() != null ? source.getNote().fullNote : null;
    }


    /**
     * Converts this Jackson-friendly adapted gamer object into the model's {@code Gamer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted gamer.
     */
    public Gamer toModelType() throws IllegalValueException {
        validateRequiredFields();
        validateFieldValues();

        // Optional fields can be null when omitted by the user, so we guard object construction to avoid null failures.
        final Name modelName = name != null ? new Name(normalizeSpacedValue(name)) : null;
        final GamerTag modelGamerTag = new GamerTag(gamerTag);
        final Phone modelPhone = phone != null ? new Phone(normalizeSpacedValue(phone)) : null;
        final Email modelEmail = email != null ? new Email(email) : null;
        final Group modelGroup = group != null ? new Group(group) : null;
        final Server modelServer = server != null ? new Server(server) : null;
        final Favourite modelFavourite = new Favourite(favourite != null ? favourite : false);
        final Country modelCountry = country != null ? new Country(normalizeSpacedValue(country)) : null;
        final Region modelRegion = region != null ? new Region(region) : null;
        final Note modelNote = note != null ? new Note(note) : null;

        return new Gamer(modelName, modelGamerTag, modelPhone, modelEmail,
                modelGroup, modelServer, modelFavourite, modelCountry, modelRegion, modelNote);
    }

    private void validateRequiredFields() throws IllegalValueException {
        if (gamerTag == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, GamerTag.class.getSimpleName()));
        }
    }

    private void validateFieldValues() throws IllegalValueException {
        if (!GamerTag.isValidGamerTag(gamerTag)) {
            throw new IllegalValueException(GamerTag.MESSAGE_CONSTRAINTS);
        }
        if (name != null && !Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        if (email != null && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        if (group != null && !Group.isValidGroup(group)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        if (server != null && !Server.isValidServer(server)) {
            throw new IllegalValueException(Server.MESSAGE_CONSTRAINTS);
        }
        if (country != null && !Country.isValidCountry(country)) {
            throw new IllegalValueException(Country.MESSAGE_CONSTRAINTS);
        }
        if (region != null && !Region.isValidRegion(region)) {
            throw new IllegalValueException(Region.MESSAGE_CONSTRAINTS);
        }
        if (note != null && !Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
    }

    private static String normalizeSpacedValue(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }
}
