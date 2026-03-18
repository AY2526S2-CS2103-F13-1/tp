package seedu.blockbook.model.gamer;

import static seedu.blockbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.blockbook.commons.util.ToStringBuilder;

/**
 * Represents a Gamer in BlockBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Gamer {

    // Identity fields
    private final Name name;
    private final GamerTag gamerTag;
    // private final Phone phone;
    // private final Email email;

    // Data fields
    // private final Region region;

    /**
     * Every field must be present and not null.
     */
    public Gamer(Name name, GamerTag gamerTag) {
        requireAllNonNull(name, gamerTag);
        this.name = name;
        this.gamerTag = gamerTag;
        //this.region = region;
        //this.phone = phone;
        //this.email = email;
    }

    public Name getName() {
        return name;
    }

    public GamerTag getGamerTag() {
        return gamerTag;
    }

    // public Region getRegion() {
    // return region;
    // }

    // public Phone getPhone() {
    // return phone;
    // }

    // public Email getEmail() {
    // return email;
    // }


    /**
     * Returns true if both gamers have the same name.
     * This defines a weaker notion of equality between two gamers.
     */
    public boolean isSameGamer(Gamer otherGamer) {
        if (otherGamer == this) {
            return true;
        }

        return otherGamer != null
                && otherGamer.getGamerTag().equals(getGamerTag());
    }

    /**
     * Returns true if both gamers have the same identity and data fields.
     * This defines a stronger notion of equality between two gamers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Gamer)) {
            return false;
        }

        Gamer otherGamer = (Gamer) other;
        return name.equals(otherGamer.name)
                && gamerTag.equals(otherGamer.gamerTag);
        //        && phone.equals(otherGamer.phone)
        //        && email.equals(otherGamer.email);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gamerTag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("gamertag", gamerTag)
                // .add("region", region)
                // .add("phone", phone)
                // .add("email", email)
                .toString();
    }

}

