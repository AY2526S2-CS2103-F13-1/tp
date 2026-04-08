package seedu.blockbook.commons.util;

import java.util.Random;

/**
 * Utility methods for confirmation code generation.
 */
public final class ConfirmationCodeUtil {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;

    private ConfirmationCodeUtil() {}

    /**
     * Generates a random confirmation code consisting of 6 alphanumeric characters,
     * prefixed with a space.
     */
    public static String generateConfirmationCode() {
        StringBuilder confirmationCode = new StringBuilder();
        confirmationCode.append(" ");
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            confirmationCode.append(CHARACTERS.charAt(index));
        }
        return confirmationCode.toString();
    }
}
