package seedu.blockbook.model.util;

import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.ReadOnlyBlockBook;
import seedu.blockbook.model.gamer.Gamer;

/**
 * Contains utility methods for populating {@code BlockBook} with sample data.
 */
public class SampleDataUtil {
    public static Gamer[] getSampleGamers() {
        return new Gamer[] {
            // new Gamer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
            //     new Address("Blk 30 Geylang Street 29, #06-40"),
            //     getTagSet("friends")),
            // new Gamer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
            //     new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            //     getTagSet("colleagues", "friends")),
            // new Gamer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
            //     new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            //     getTagSet("neighbours")),
            // new Gamer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
            //     new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            //     getTagSet("family")),
            // new Gamer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
            //     new Address("Blk 47 Tampines Street 20, #17-35"),
            //     getTagSet("classmates")),
            // new Gamer(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
            //     new Address("Blk 45 Aljunied Street 85, #11-31"),
            //     getTagSet("colleagues"))
        };
    }

    public static ReadOnlyBlockBook getSampleBlockBook() {
        BlockBook sampleBlockBook = new BlockBook();
        for (Gamer sampleGamer : getSampleGamers()) {
            sampleBlockBook.addGamer(sampleGamer);
        }
        return sampleBlockBook;
    }

}

