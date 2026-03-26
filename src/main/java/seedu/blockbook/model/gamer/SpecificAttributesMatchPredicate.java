package seedu.blockbook.model.gamer;

import java.util.function.Predicate;

/**
 * Tests that a {@code Gamer}'s specific attributes match the given keywords. If
 * a keyword is null, that attribute is not checked (ignored).
 */
public class SpecificAttributesMatchPredicate implements Predicate<Gamer> {

    private final String nameKeyword;
    private final String gamertagKeyword;
    private final String phoneKeyword;
    private final String emailKeyword;
    private final String groupKeyword;
    private final String serverKeyword;
    private final String favouriteKeyword;
    private final String countryKeyword;
    private final String regionKeyword;

    /**
     * Tests that a {@code Gamer}'s attributes partially match the given keywords.
     * For each non-null keyword, the corresponding attribute must contain that
     * keyword (case-insensitive) for this predicate to evaluate to {@code true}.
     * Attributes whose keywords are {@code null} are not checked (ignored).
     *
     * @param nameKeyword the keyword to match against the gamer's name
     * @param gamertagKeyword the keyword to match against the gamer's gamertag
     * @param phoneKeyword the keyword to match against the gamer's phone
     * @param emailKeyword the keyword to match against the gamer's email
     * @param groupKeyword the keyword to match against the gamer's group
     * @param serverKeyword the keyword to match against the gamer's server
     * @param favouriteKeyword the keyword to match against the gamer's favourite
     * @param countryKeyword the keyword to match against the gamer's country
     * @param regionKeyword the keyword to match against the gamer's region
     */
    public SpecificAttributesMatchPredicate(String nameKeyword, String gamertagKeyword,
            String phoneKeyword, String emailKeyword,
            String groupKeyword, String serverKeyword,
            String favouriteKeyword,
            String countryKeyword, String regionKeyword) {
        this.nameKeyword = nameKeyword;
        this.gamertagKeyword = gamertagKeyword;
        this.phoneKeyword = phoneKeyword;
        this.emailKeyword = emailKeyword;
        this.groupKeyword = groupKeyword;
        this.serverKeyword = serverKeyword;
        this.favouriteKeyword = favouriteKeyword;
        this.countryKeyword = countryKeyword;
        this.regionKeyword = regionKeyword;
    }

    @Override
    public boolean test(Gamer gamer) {
        return (nameKeyword == null || attributeContains(gamer.getName(), nameKeyword))
                && (gamertagKeyword == null || attributeContains(gamer.getGamerTag(), gamertagKeyword))
                && (phoneKeyword == null || attributeContains(gamer.getPhone(), phoneKeyword))
                && (emailKeyword == null || attributeContains(gamer.getEmail(), emailKeyword))
                && (groupKeyword == null || attributeContains(gamer.getGroup(), groupKeyword))
                && (serverKeyword == null || attributeContains(gamer.getServer(), serverKeyword))
                && (favouriteKeyword == null || attributeContains(gamer.getFavourite(), favouriteKeyword))
                && (countryKeyword == null || attributeContains(gamer.getCountry(), countryKeyword))
                && (regionKeyword == null || attributeContains(gamer.getRegion(), regionKeyword));
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
        if (!(other instanceof SpecificAttributesMatchPredicate)) {
            return false;
        }
        SpecificAttributesMatchPredicate o = (SpecificAttributesMatchPredicate) other;

        return java.util.Objects.equals(nameKeyword, o.nameKeyword)
                && java.util.Objects.equals(gamertagKeyword, o.gamertagKeyword)
                && java.util.Objects.equals(phoneKeyword, o.phoneKeyword)
                && java.util.Objects.equals(emailKeyword, o.emailKeyword)
                && java.util.Objects.equals(groupKeyword, o.groupKeyword)
                && java.util.Objects.equals(serverKeyword, o.serverKeyword)
                && java.util.Objects.equals(favouriteKeyword, o.favouriteKeyword)
                && java.util.Objects.equals(countryKeyword, o.countryKeyword)
                && java.util.Objects.equals(regionKeyword, o.regionKeyword);
    }
}
