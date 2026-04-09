package seedu.blockbook.model.util;

import java.util.List;

import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;
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
 * Contains utility methods for populating {@code BlockBook} with sample data.
 */
public class SampleDataUtil {
    public static Gamer[] getSampleGamers() {
        return new Gamer[] {
            new Gamer(
                    new Name("Steve"),
                    new GamerTag("BlockBreaker99"),
                    new Phone("90000002"),
                    new Email("steve@blockbook.dev"),
                    List.of(new Group("Builders"), new Group("Explorers"), new Group("Raid Team")),
                    new Server("srv2.gamehub.net"),
                    new Favourite(true),
                    new Country("USA"),
                    new Region("NA"),
                    new Note("loves_redstone")
            ),
            new Gamer(
                    new Name("Alex"),
                    new GamerTag("CraftyAlex"),
                    null,
                    new Email("alex@craft.net"),
                    List.of(new Group("Explorers")),
                    null,
                    new Favourite(true),
                    new Country("UK"),
                    new Region("EU"),
                    null
            ),
            new Gamer(
                    null,
                    new GamerTag("EnderSlayerX"),
                    new Phone("90000003"),
                    null,
                    List.of(new Group("Raid Team")),
                    new Server("end.net"),
                    new Favourite(false),
                    new Country("Germany"),
                    new Region("EU"),
                    new Note("boss_hunter")
            ),
            new Gamer(
                    new Name("CreeperKid"),
                    new GamerTag("SSSSBoom"),
                    null,
                    null,
                    List.of(),
                    new Server("srv3.gamehub.net"),
                    new Favourite(false),
                    null,
                    null,
                    null
            ),
            new Gamer(
                    new Name("MinerMax"),
                    new GamerTag("DeepDigDude"),
                    new Phone("90000004"),
                    new Email("miner@cave.net"),
                    List.of(new Group("Miners")),
                    new Server("caves.net"),
                    new Favourite(true),
                    new Country("Australia"),
                    new Region("OCEANIA"),
                    new Note("strip_mining")
            )
        };
    }

    public static Group[] getSampleGroups() {
        return new Group[] {
            new Group("Builders"),
            new Group("Explorers"),
            new Group("Raid Team"),
            new Group("Miners"),
            new Group("Adventurers"),
            new Group("Traders"),
            new Group("PvP"),
            new Group("Enchanters"),
            new Group("Farmers"),
            new Group("Alchemists"),
            new Group("Grinders")
        };
    }

    public static ReadOnlyBlockBook getSampleBlockBook() {
        BlockBook sampleBlockBook = new BlockBook();
        for (Group sampleGroup : getSampleGroups()) {
            sampleBlockBook.addGroup(sampleGroup);
        }
        for (Gamer sampleGamer : getSampleGamers()) {
            sampleBlockBook.addGamer(sampleGamer);
        }
        return sampleBlockBook;
    }

}
