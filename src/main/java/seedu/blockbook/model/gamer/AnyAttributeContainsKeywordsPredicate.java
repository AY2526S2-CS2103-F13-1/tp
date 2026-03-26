package seedu.blockbook.model.gamer;

import java.util.function.Predicate;

import seedu.blockbook.commons.util.ToStringBuilder;

/**
 * Tests that any {@code Gamer} attribute matches the keyword given.
 */
public class AnyAttributeContainsKeywordsPredicate implements Predicate<Gamer> {
    private final String keyword;

    public AnyAttributeContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Gamer gamer) {
        return attributeContains(gamer.getName(), keyword)
                || attributeContains(gamer.getGamerTag(), keyword)
                || attributeContains(gamer.getPhone(), keyword)
                || attributeContains(gamer.getEmail(), keyword)
                || attributeContains(gamer.getGroups(), keyword)
                || attributeContains(gamer.getServer(), keyword)
                || attributeContains(gamer.getNote(), keyword)
                // Favourites should not be in the any attribute partial search
                // || attributeContains(gamer.getFavourite(), keyword)
                || attributeContains(gamer.getCountry(), keyword)
                || attributeContains(gamer.getRegion(), keyword);
    }
    /**
     * Checks if attribute contains keyword
     * @param attribute the attribute value to check
     * @param keyword the keyword to search for
     * @return true if the attribute's string representation contains the keyword, case-insensitive
     */
    private boolean attributeContains(Object attribute, String keyword) {
        if (attribute == null) {
            return false;
        }
        return attribute.toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnyAttributeContainsKeywordsPredicate)) {
            return false;
        }

        AnyAttributeContainsKeywordsPredicate otherAnyAttributeContainsKeywordsPredicate =
                (AnyAttributeContainsKeywordsPredicate) other;
        return keyword.equals(otherAnyAttributeContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}

