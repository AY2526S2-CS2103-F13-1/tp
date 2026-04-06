package seedu.blockbook.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    public static final Prefix PREFIX_GAMERTAG = new Prefix("gamertag/", "g/");
    public static final Prefix PREFIX_NAME = new Prefix("name/", "n/");
    public static final Prefix PREFIX_PHONE = new Prefix("phone/", "p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("email/", "e/");
    public static final Prefix PREFIX_GROUP = new Prefix("group/", "grp/");
    public static final Prefix PREFIX_SERVER = new Prefix("server/", "s/");
    public static final Prefix PREFIX_FAVOURITE = new Prefix("favourite/", "f/");
    public static final Prefix PREFIX_COUNTRY = new Prefix("country/", "c/");
    public static final Prefix PREFIX_REGION = new Prefix("region/", "r/");
    public static final Prefix PREFIX_NOTE = new Prefix("note/", null);
}
