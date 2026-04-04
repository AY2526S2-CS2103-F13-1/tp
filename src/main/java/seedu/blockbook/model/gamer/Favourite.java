package seedu.blockbook.model.gamer;

/**
 * Represents whether a Gamer is favourited in the BlockBook.
 * Guarantees: immutable.
 */
public class Favourite {

    public final boolean isFavourite;

    /**
     * Constructs a Favourite.
     *
     * @param isFavourite Whether the gamer is favourited.
     */
    public Favourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    /**
     * Returns true if this favourite status represents a favourited gamer.
     */
    public boolean isFav() {
        return isFavourite;
    }

    @Override
    public String toString() {
        return isFavourite ? "Yes" : "No";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Favourite)) {
            return false;
        }

        Favourite otherFavourite = (Favourite) other;
        return isFavourite == otherFavourite.isFavourite;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isFavourite);
    }
}
