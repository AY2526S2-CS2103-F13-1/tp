package seedu.blockbook.logic.parser;

import java.util.Objects;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;
    private final String alias;

    /**
     * Creates a new Prefix with the specified prefix string and alias.
     * @param prefix
     * @param alias
     */
    public Prefix(String prefix, String alias) {
        this.prefix = prefix;
        this.alias = alias;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, alias);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Prefix)) {
            return false;
        }

        Prefix otherPrefix = (Prefix) other;
        return Objects.equals(prefix, otherPrefix.prefix)
                && Objects.equals(alias, otherPrefix.alias);
    }
}
