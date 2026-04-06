package seedu.blockbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.blockbook.commons.exceptions.IllegalValueException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * An Immutable BlockBook that is serializable to JSON format.
 */
@JsonRootName(value = "gamers")
class JsonSerializableBlockBook {

    public static final String MESSAGE_DUPLICATE_GAMER = "Gamers list contains duplicate gamer(s).";
    public static final String MESSAGE_DUPLICATE_GROUP = "Group list contains duplicate group(s).";

    private final List<JsonAdaptedGamer> gamers = new ArrayList<>();
    private final List<String> blockbookgroups = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBlockBook} with the given gamers.
     */
    @JsonCreator
    public JsonSerializableBlockBook(@JsonProperty("gamers") List<JsonAdaptedGamer> gamers,
                                     @JsonProperty("blockbookgroups") List<String> blockbookgroups) {
        this.gamers.addAll(gamers);
        if (blockbookgroups != null) {
            this.blockbookgroups.addAll(blockbookgroups);
        }
    }

    /**
     * Converts a given {@code ReadOnlyBlockBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBlockBook}.
     */
    public JsonSerializableBlockBook(ReadOnlyBlockBook source) {
        List<Group> groupList = new ArrayList<>(source.getGroupList());
        blockbookgroups.addAll(groupList.stream().map(Group::toString).collect(Collectors.toList()));
        gamers.addAll(source.getGamerList().stream()
                .map(gamer -> new JsonAdaptedGamer(gamer, groupList))
                .collect(Collectors.toList()));
    }

    /**
     * Converts this block book into the model's {@code BlockBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BlockBook toModelType() throws IllegalValueException {
        BlockBook blockBook = new BlockBook();

        for (String groupName : blockbookgroups) {
            String normalizedGroup = normalizeSpacedValue(groupName);
            if (!Group.isValidGroup(normalizedGroup)) {
                throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
            }
            Group group = new Group(normalizedGroup);
            if (blockBook.hasGroup(group)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROUP);
            }
            blockBook.addGroup(group);
        }

        List<Group> groupList = new ArrayList<>(blockBook.getGroupList());
        for (JsonAdaptedGamer jsonAdaptedGamer : gamers) {
            Gamer gamer = jsonAdaptedGamer.toModelType(groupList);
            if (blockBook.hasGamer(gamer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GAMER);
            }
            blockBook.addGamer(gamer);
        }
        return blockBook;
    }

    private static String normalizeSpacedValue(String value) {
        return Objects.requireNonNull(value).trim().replaceAll("\\s+", " ");
    }

}
