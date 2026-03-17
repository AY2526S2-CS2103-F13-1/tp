package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.gamer.Gamer;
import seedu.address.model.gamer.GamerTag;
import seedu.address.model.gamer.Name;

/**
 * Jackson-friendly version of {@link Gamer}.
 */
class JsonAdaptedGamer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Gamer's %s field is missing!";

    private final String name;
    private final String gamerTag;
    //private final String phone;
    //private final String email;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedGamer(@JsonProperty("name") String name, @JsonProperty("gamerTag") String gamerTag) {
        this.name = name;
        this.gamerTag = gamerTag;
        // this.phone = phone;
        // this.email = email;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedGamer(Gamer source) {
        name = source.getName().fullName;
        gamerTag = source.getGamerTag().fullGamerTag;
        // phone = source.getPhone().value;
        // email = source.getEmail().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Gamer toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (gamerTag == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, GamerTag.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!GamerTag.isValidGamerTag(gamerTag)) {
            throw new IllegalValueException(GamerTag.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final GamerTag modelGamerTag = new GamerTag(gamerTag);

        // if (phone == null) {
        // throw new IllegalValueException(
        // String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        // }
        // if (!Phone.isValidPhone(phone)) {
        // throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        // }
        // final Phone modelPhone = new Phone(phone);
        // if (email == null) {
        // throw new IllegalValueException(
        // String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        // }
        // if (!Email.isValidEmail(email)) {
        // throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        // }
        // final Email modelEmail = new Email(email);

        // if (address == null) {
        // throw new IllegalValueException(
        // String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        // }
        // if (!Address.isValidAddress(address)) {
        //     throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        // }
        // Region modelRegion = new Region("Singapore");

        return new Gamer(modelName, modelGamerTag);
    }

}
