package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BlockBook;
import seedu.address.model.ReadOnlyBlockBook;
import seedu.address.model.gamer.Gamer;

/**
 * An Immutable BlockBook that is serializable to JSON format.
 */
@JsonRootName(value = "gamers")
class JsonSerializableBlockBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Gamers list contains duplicate person(s).";

    private final List<JsonAdaptedGamer> gamers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBlockBook} with the given gamers.
     */
    @JsonCreator
    public JsonSerializableBlockBook(@JsonProperty("gamers") List<JsonAdaptedGamer> gamers) {
        this.gamers.addAll(gamers);
    }

    /**
     * Converts a given {@code ReadOnlyBlockBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBlockBook}.
     */
    public JsonSerializableBlockBook(ReadOnlyBlockBook source) {
        gamers.addAll(source.getGamerList().stream().map(JsonAdaptedGamer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this block book into the model's {@code BlockBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BlockBook toModelType() throws IllegalValueException {
        BlockBook addressBook = new BlockBook();
        for (JsonAdaptedGamer jsonAdaptedGamer : gamers) {
            Gamer gamer = jsonAdaptedGamer.toModelType();
            if (addressBook.hasGamer(gamer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addGamer(gamer);
        }
        return addressBook;
    }

}
