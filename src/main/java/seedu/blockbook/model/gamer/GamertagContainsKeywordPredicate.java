package seedu.blockbook.model.gamer;

import java.util.function.Predicate;

import seedu.blockbook.commons.util.StringUtil;
import seedu.blockbook.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Gamer}'s {@code Gamertag} matches the given keyword.
 */
public class GamertagContainsKeywordPredicate implements Predicate<Gamer> {
    private final String keyword;

    public GamertagContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Gamer gamer) {
        return StringUtil.containsWordIgnoreCase(gamer.getGamerTag().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GamertagContainsKeywordPredicate)) {
            return false;
        }

        GamertagContainsKeywordPredicate otherGamertagContainsKeywordPredicate =
                (GamertagContainsKeywordPredicate) other;
        return keyword.equals(otherGamertagContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
