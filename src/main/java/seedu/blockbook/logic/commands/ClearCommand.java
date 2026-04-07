package seedu.blockbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Random;

import seedu.blockbook.logic.commands.exceptions.CommandException;
import seedu.blockbook.model.BlockBook;
import seedu.blockbook.model.Model;

/**
 * Clears the BlockBook.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes every gamer from BlockBook. CANNOT BE UNDONE!\n"
            + "\nFormat: " + COMMAND_WORD;
    public static final String CONFIRMATION_MESSAGE = "----- WARNING! -----\n"
            + "THIS COMMAND WILL REMOVE ALL GAMERS FROM BLOCKBOOK!!!\n"
            + "THIS OPERATION CANNOT BE UNDONE!!!\n"
            + "If you wish to continue, enter the command below.\n"
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "BlockBook has been cleared!";
    private static String confirmationCode = null;
    private String userInput;

    /**
     * Creates a ClearCommand that clears the BlockBook.
     *
     * @param userInput Input of the ClearCommand that might be the confirmation code.
     */
    public ClearCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Generates a random confirmation code consisting of
     * 6 alphanumeric characters, prefixed with a space.
     */
    public String generateConfirmationCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder confirmationCode = new StringBuilder();
        confirmationCode.append(" ");
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            confirmationCode.append(characters.charAt(index));
        }
        return confirmationCode.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (confirmationCode == null || !confirmationCode.equals(userInput)) {
            confirmationCode = generateConfirmationCode(); // Randomize the code
            throw new CommandException(CONFIRMATION_MESSAGE + confirmationCode);
        }

        confirmationCode = null;
        model.setBlockBook(new BlockBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

