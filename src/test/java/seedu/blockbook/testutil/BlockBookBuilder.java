package seedu.blockbook.testutil;

import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.gamer.Gamer;

/**
 * A utility class to help with building BlockBook objects.
 * Example usage: <br>
 *     {@code BlockBook bb = new BlockBookBuilder().withGamer("John", "Doe").build();}
 */
public class BlockBookBuilder {

    private BlockBook blockBook;

    public BlockBookBuilder() {
        blockBook = new BlockBook();
    }

    public BlockBookBuilder(BlockBook blockBook) {
        this.blockBook = blockBook;
    }

    /**
     * Adds a new {@code Gamer} to the {@code BlockBook} that we are building.
     */
    public BlockBookBuilder withGamer(Gamer gamer) {
        blockBook.addGamer(gamer);
        return this;
    }

    public BlockBook build() {
        return blockBook;
    }
}


