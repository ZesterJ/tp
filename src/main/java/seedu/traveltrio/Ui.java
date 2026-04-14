package seedu.traveltrio;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the user interface of the application. A Ui object handles
 * all interactions with the user, including reading commands and displaying messages,
 * errors, and prompts.
 */
public class Ui {

    public static final String LOGO =
            "  _______                   _ _______   _ \n"
            + " |__   __|                 | |__   __| (_) \n"
            + "    | |_ __ __ ___   _____ | |  | |_ __ _  ___  \n"
            + "    | | '__/ _` \\ \\ / / _ \\| |  | | '__| |/ _ \\ \n"
            + "    | | | | (_| |\\ V /  __/| |  | | |  | | (_) |\n"
            + "    |_|_|  \\__,_| \\_/ \\___||_|  |_|_|  |_|\\___/ \n";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner in;

    /**
     * Initializes a Ui object and its input scanner.
     */
    public Ui () {
        this.in = new Scanner(System.in);
    }

    /**
     * Reads the next command entered by the user. Displays a context-aware prompt
     * showing the name of the currently opened trip if applicable.
     *
     * @param currentTripName The name of the trip currently being managed, or null if none.
     * @return The trimmed, lowercase string representing the user's command.
     */
    public String readCommand(String currentTripName) throws TravelTrioException {
        if (currentTripName == null) {
            System.out.print("> ");
        } else {
            System.out.print("[Opened: " + currentTripName + "] > ");
        }
        if (!in.hasNextLine()) {
            throw new TravelTrioException("End of input reached.");
        }
        String input = in.nextLine();
        return input.trim().toLowerCase();
    }

    /**
     * Displays the welcome message and application logo to the user upon startup.
     */
    public void showWelcomeMessage() {
        System.out.println("Welcome to \n" + LOGO);
        System.out.println("How can I help you plan today?");
        System.out.println("Commands: \n" +
                "addtrip, listtrip, opentrip, deletetrip, exporttrip, importtrip, \n" +
                "addactivity, listactivity, editactivity, deleteactivity, nextactivity, addremark, \n" +
                "setbudget, setexpense, setcurrency, budgetsummary, budgetchart, listexpense, setdailylimit,\n" +
                "additem, listitems, checkitem, deleteitem, \n" +
                "help, exit");
    }

    /**
     * Prompts the user for a required text field. Will repeat the prompt if
     * the user provides an empty input.
     *
     * @param label The descriptive label for the requested input.
     * @return The non-empty string provided by the user.
     */
    public String promptField(String label) throws TravelTrioException {
        while (true) {
            System.out.print(label + ": ");
            if (!in.hasNextLine()) {
                throw new TravelTrioException("End of input reached.");
            }
            String input = in.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Prompts the user for a text field that can be left blank.
     *
     * @param label The descriptive label for the requested input.
     * @return The string provided by the user, which may be empty.
     */
    public String promptOptionalField(String label)  throws TravelTrioException {
        System.out.print(label + ": ");
        if (!in.hasNextLine()) {
            throw new TravelTrioException("End of input reached.");
        }
        return in.nextLine().trim();
    }

    /**
     * Prompts the user for an integer value. Will repeat the prompt until
     * a valid integer is entered.
     *
     * @param label The descriptive label for the requested input.
     * @return The integer value provided by the user.
     */
    public int promptInt(String label) throws TravelTrioException {
        while (true) {
            System.out.print(label + ": ");
            if (!in.hasNextLine()) {
                throw new TravelTrioException("End of input reached.");
            }
            String input = in.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user for a double value. Will repeat the prompt until
     * a valid decimal number is entered.
     *
     * @param label The descriptive label for the requested input.
     * @return The double value provided by the user.
     */
    public double promptDouble(String label) throws TravelTrioException {
        while (true) {
            System.out.print(label + ": ");
            if (!in.hasNextLine()) {
                throw new TravelTrioException("End of input reached.");
            }
            String input = in.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Prompts the user for a date. Validates the input against the YYYY-MM-DD format.
     *
     * @param label The descriptive label for the requested input.
     * @return A valid date string in YYYY-MM-DD format.
     */
    public String promptDate(String label) throws TravelTrioException {
        while (true) {
            System.out.print(label + " (YYYY-MM-DD): ");
            if (!in.hasNextLine()) {
                throw new TravelTrioException("End of input reached.");
            }
            String input = in.nextLine().trim();
            try {
                LocalDate.parse(input, DATE_FORMATTER);
                return input; // It's a valid date.
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD (e.g., 2026-12-25).");
            }
        }
    }

    /**
     * Prompts the user for a time. Validates the input against the 24-hour HH:MM format.
     *
     * @param label The descriptive label for the requested input.
     * @return A valid time string in HH:MM format.
     */
    public String promptTime(String label) throws TravelTrioException {
        while (true) {
            System.out.print(label + " (HH:MM): ");
            if (!in.hasNextLine()) {
                throw new TravelTrioException("End of input reached.");
            }
            String input = in.nextLine().trim();
            try {
                LocalTime.parse(input, TIME_FORMATTER);
                return input; // It's a valid time.
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use 24-hour HH:MM (e.g., 14:30).");
            }
        }
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The text to be printed.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message formatted with an error prefix.
     *
     * @param message The error description to be printed.
     */
    public void showError(String message) {
        System.out.println("Error. " + message);
    }

    /**
     * Prints a visual divider line to the console.
     */
    public void printDivider(){
        System.out.println("===========================================================");
    }

    /**
     * Displays a message to the user enclosed within two visual divider lines.
     *
     * @param message The text to be printed.
     */
    public void showMessageWithDivider(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

}
