package seedu.blockbook.testutil;

//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.blockbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.gamer.Gamer;
import seedu.blockbook.model.gamer.Group;

/**
 * A utility class containing a list of {@code Gamer} objects to be used in tests.
 */
public class TypicalGamers {

    public static final Gamer ALICE = new GamerBuilder().withName("Alice Pauline").withGamerTag("Herobrine").build();
    public static final Gamer BENSON = new GamerBuilder().withName("Benson Meier").withGamerTag("Herobrine2").build();
    public static final Gamer CARL = new GamerBuilder().withName("Carl Kurz").withGamerTag("Herobrine3").build();
    public static final Gamer DANIEL = new GamerBuilder().withName("Daniel Meier").withGamerTag("Herobrine4").build();
    public static final Gamer ELLE = new GamerBuilder().withName("Elle Meyer").withGamerTag("Herobrine5").build();
    public static final Gamer FIONA = new GamerBuilder().withName("Fiona Kunz").withGamerTag("Herobrine6").build();
    public static final Gamer GEORGE = new GamerBuilder().withName("George Best").withGamerTag("Herobrine7").build();

    // Manually added
    public static final Gamer HOON = new GamerBuilder().withName("Hoon Meier").withGamerTag("Herobrine8").build();
    public static final Gamer IDA = new GamerBuilder().withName("Ida Mueller").withGamerTag("Herobrine9").build();

    // Manually added - Gamer's details found in {@code CommandTestUtil}
    public static final Gamer AMY = new GamerBuilder().withName(VALID_NAME_AMY).withGamerTag("Herobrine10").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGamers() {} // prevents instantiation

    /**
     * Returns a {@code BlockBook} with all the typical gamers.
     */
    public static BlockBook getTypicalBlockBook() {
        BlockBook blockBook = new BlockBook();
        for (Gamer gamer : getTypicalGamers()) {
            for (Group group : gamer.getGroups()) {
                if (!blockBook.hasGroup(group)) {
                    blockBook.addGroup(group);
                }
            }
            blockBook.addGamer(gamer);
        }
        return blockBook;
    }

    public static List<Gamer> getTypicalGamers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
