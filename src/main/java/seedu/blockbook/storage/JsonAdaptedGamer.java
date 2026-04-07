package seedu.blockbook.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    private final List<Integer> groups = new ArrayList<>();
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
                            @JsonProperty("groups") List<Integer> groups,
                            @JsonProperty("server") String server,
                            @JsonProperty("favourite") Boolean favourite,
                            @JsonProperty("country") String country,
                            @JsonProperty("region") String region,
                            @JsonProperty("note") String note) {
        this.name = name;
        this.gamerTag = gamerTag;
        this.phone = phone;
        this.email = email;
        if (groups != null) {
            this.groups.addAll(groups);
        }
        this.server = server;
        this.favourite = favourite;
        this.country = country;
        this.region = region;
        this.note = note;
    }

    /**
     * Converts a given {@code Gamer} into this class for Jackson use.
     */
    public JsonAdaptedGamer(Gamer source, List<Group> groupList) {
        name = source.getName() != null ? source.getName().fullName : null;
        gamerTag = source.getGamerTag() != null ? source.getGamerTag().fullGamerTag : null;
        phone = source.getPhone() != null ? source.getPhone().fullPhone : null;
        email = source.getEmail() != null ? source.getEmail().fullEmail : null;
        server = source.getServer() != null ? source.getServer().fullServer : null;
        favourite = source.getFavourite() != null ? source.getFavourite().isFav() : false;
        country = source.getCountry() != null ? source.getCountry().fullCountry : null;
        region = source.getRegion() != null ? source.getRegion().fullRegion : null;
        note = source.getNote() != null ? source.getNote().fullNote : null;

        Map<String, Integer> groupIndexMap = new HashMap<>();
        for (int i = 0; i < groupList.size(); i++) {
            groupIndexMap.put(groupList.get(i).toString().toLowerCase(), i);
        }

        for (Group group : source.getGroups()) {
            Integer index = groupIndexMap.get(group.toString().toLowerCase());
            if (index == null) {
                throw new IllegalArgumentException("Unknown group referenced by gamer: " + group);
            }
            groups.add(index);
        }
    }

    /**
     * Converts this Jackson-friendly adapted gamer object into the model's {@code Gamer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted gamer.
     */
    public Gamer toModelType(List<Group> groupList) throws IllegalValueException {
        validateRequiredFields();
        validateFieldValues();
        Objects.requireNonNull(groupList);

        List<Group> modelGroups = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (Integer index : groups) {
            if (index == null) {
                throw new IllegalValueException("Group index cannot be null.");
            }
            if (index < 0 || index >= groupList.size()) {
                throw new IllegalValueException("Group index out of range: " + index);
            }
            if (!seen.add(index)) {
                throw new IllegalValueException("Duplicate group index: " + index);
            }
            modelGroups.add(groupList.get(index));
        }

        // Optional fields can be null when omitted by the user, so we guard object construction to avoid null failures.
        final Name modelName = name != null ? new Name(normalizeCapitalizedWords(name)) : null;
        final GamerTag modelGamerTag = new GamerTag(gamerTag);
        final Phone modelPhone = phone != null ? new Phone(normalizeSpacedValue(phone)) : null;
        final Email modelEmail = email != null ? new Email(email) : null;
        final Server modelServer = server != null ? new Server(server) : null;
        final Favourite modelFavourite = new Favourite(favourite != null ? favourite : false);
        final Country modelCountry = country != null ? new Country(normalizeCapitalizedWords(country)) : null;
        final Region modelRegion = region != null ? new Region(region) : null;
        final Note modelNote = note != null ? new Note(note) : null;

        return new Gamer(modelName, modelGamerTag, modelPhone, modelEmail,
                modelGroups, modelServer, modelFavourite, modelCountry, modelRegion, modelNote);
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

    /**
     * Trims the input, collapses repeated spaces, and capitalizes each word segment.
     * Word segments are restarted after spaces, hyphens, and apostrophes.
     */
    private static String normalizeCapitalizedWords(String value) {
        String collapsed = value.trim().replaceAll("\\s+", " ");
        StringBuilder builder = new StringBuilder(collapsed.length());
        boolean capitalizeNext = true;
        for (int i = 0; i < collapsed.length(); i++) {
            char currentChar = collapsed.charAt(i);
            if (Character.isLetter(currentChar)) {
                builder.append(capitalizeNext
                        ? Character.toUpperCase(currentChar)
                        : Character.toLowerCase(currentChar));
                capitalizeNext = false;
            } else {
                builder.append(currentChar);
                capitalizeNext = currentChar == ' ' || currentChar == '-' || currentChar == '\'';
            }
        }
        return builder.toString();
    }
}
