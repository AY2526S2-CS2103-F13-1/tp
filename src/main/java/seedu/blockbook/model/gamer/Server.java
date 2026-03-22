package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's server in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidServer(String)}
 */
public class Server {


    public static final String MESSAGE_CONSTRAINTS =
            "Server should only contain letters, numbers, periods (.), hyphens, and colons (:), "
                    + "and be at most 50 characters.";
    // "^[a-zA-Z0-9._:-]{1,50}$" if want underscores included
    // "^[a-zA-Z0-9._: -]{1,50}$" if want underscores and spaces included
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9.:-]{1,50}$";
    public final String fullServer;

    /**
     * Constructs a {@code Server}.
     *
     * @param server A valid server.
     */
    public Server(String server) {
        requireNonNull(server);
        checkArgument(isValidServer(server), MESSAGE_CONSTRAINTS);
        fullServer = server;
    }

    /**
     * Returns true if a given string is a valid server.
     */
    public static boolean isValidServer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullServer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Server)) {
            return false;
        }

        Server otherServer = (Server) other;
        return fullServer.equals(otherServer.fullServer);
    }

    @Override
    public int hashCode() {
        return fullServer.hashCode();
    }

}

