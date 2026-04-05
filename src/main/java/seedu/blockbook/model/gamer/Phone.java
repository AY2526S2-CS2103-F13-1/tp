package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's phone number in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {
    public static final String MESSAGE_CONSTRAINTS =
            "Phone number should only contain digits, spaces, hyphens, and an optional plus sign (+), "
                    + "must contain at least 3 digits, cannot exceed 15 digits, and be at most 30 characters long.";
    public static final String VALIDATION_LAX_REGEX = "^[0-9+()\\- ]+$";
    public final String fullPhone;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        fullPhone = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String input) {
        return validatePhone(input) == null;
    }

    /**
     * Returns the validation error message for the given phone number input.
     */
    public static String getPhoneValidationError(String input) {
        return validatePhone(input);
    }

    /**
     * Returns a validation error message if the phone number is invalid, or null otherwise.
     */
    private static String validatePhone(String input) {
        requireNonNull(input);

        if (input.isEmpty()) {
            return "Phone number cannot be empty";
        }

        if (input.length() > 30) {
            return "Phone number cannot exceed 30 characters";
        }

        // Allows digits, spaces, hyphens and plus signs only.
        if (!input.matches("^[+\\d\\- ]+$")) {
            return "Phone number can only contain digits, hyphens, spaces and an optional '+'";
        }

        // Check for plus signs
        int plusCount = input.length() - input.replace("+", "").length();
        if (plusCount > 1) {
            return "Phone number can contain at most one '+'";
        }
        if (plusCount == 1 && input.charAt(0) != '+') {
            return "'+' must be at the beginning";
        }

        if (input.startsWith("+") && input.length() > 1 && !Character.isDigit(input.charAt(1))) {
            return "'+' must be followed by a digit";
        }

        if (input.startsWith("-")) {
            return "Phone number cannot start with a hyphen";
        }

        if (input.endsWith("-")) {
            return "Phone number cannot end with a hyphen";
        }

        // Check consecutive separators
        if (input.contains("--") || input.contains("- ") || input.contains(" -")) {
            return "Hyphens in a phone number must be used between digits only.";
        }

        String digits = input.replaceAll("[^0-9]", "");
        if (digits.length() < 3) {
            return "Phone number must contain at least 3 digits";
        }
        if (digits.length() > 15) {
            return "Phone number cannot exceed 15 digits";
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid lax phone number, where it will allow search
     * if keyword has numbers, spaces, hyphens, plus signs (+), and parentheses
     */
    public static boolean isValidLaxPhone(String test) {
        return test.matches(VALIDATION_LAX_REGEX);
    }

    @Override
    public String toString() {
        return fullPhone;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return fullPhone.equals(otherPhone.fullPhone);
    }

    @Override
    public int hashCode() {
        return fullPhone.hashCode();
    }

}

