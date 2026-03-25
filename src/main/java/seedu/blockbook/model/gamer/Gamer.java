package seedu.blockbook.model.gamer;

import static seedu.blockbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.blockbook.commons.util.ToStringBuilder;

/**
 * Represents a Gamer in BlockBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Gamer {

    // Identity fields
    private final Name name;
    private final GamerTag gamerTag;
    private final Phone phone;
    private final Email email;
    private final Set<Group> groups = new HashSet<>();
    private final Server server;
    private final Favourite favourite;
    private final Country country;
    private final Region region;
    private final Note note;

    /**
     * Every field must be present and not null.
     */
    public Gamer(Name name, GamerTag gamerTag, Phone phone, Email email,
                 Set<Group> groups, Server server, Favourite favourite,
                 Country country, Region region, Note note) {
        requireAllNonNull(gamerTag);
        this.name = name;
        this.gamerTag = gamerTag;
        this.phone = phone;
        this.email = email;
        this.groups.addAll(groups);
        this.server = server;
        this.favourite = favourite;
        this.country = country;
        this.region = region;
        this.note = note;
    }

    public Name getName() {
        return name;
    }

    public GamerTag getGamerTag() {
        return gamerTag;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }
    /**
     * Returns an immutable group set
     */
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups);
    }
    public Server getServer() {
        return server;
    }

    public Favourite getFavourite() {
        return favourite;
    }

    public Country getCountry() {
        return country;
    }

    public Region getRegion() {
        return region;
    }

    public Note getNote() {
        return note;
    }


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
        // prevent nullpointerexception
        return Objects.equals(name, otherGamer.name)
                && Objects.equals(gamerTag, otherGamer.gamerTag)
                && Objects.equals(phone, otherGamer.phone)
                && Objects.equals(email, otherGamer.email)
                && Objects.equals(groups, otherGamer.groups)
                && Objects.equals(server, otherGamer.server)
                && Objects.equals(favourite, otherGamer.favourite)
                && Objects.equals(country, otherGamer.country)
                && Objects.equals(region, otherGamer.region)
                && Objects.equals(note, otherGamer.note);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        // return Objects.hash(name, gamerTag);
        return Objects.hash(name, gamerTag, phone, email, groups, server, favourite, country, region, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("gamertag", gamerTag)
                .add("phone", phone)
                .add("email", email)
                .add("groups", groups)
                .add("server", server)
                .add("favourite", favourite)
                .add("country", country)
                .add("region", region)
                .add("note", note)
                .toString();
    }

}

