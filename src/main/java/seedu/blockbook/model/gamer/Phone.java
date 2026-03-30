package seedu.blockbook.model.gamer;

import static java.util.Objects.requireNonNull;
import static seedu.blockbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Gamer's phone number in the BlockBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {
    public static final String MESSAGE_CONSTRAINTS =
            "Phone number should only contain numbers, spaces, hyphens, plus sign (+), parentheses, "
                    + "must contain at least one digit, and be at most 30 characters.";
    public static final String VALIDATION_REGEX = "^(?=.*\\d)[0-9+()\\- ]{1,30}$";
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
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
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

