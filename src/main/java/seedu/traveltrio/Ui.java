package seedu.traveltrio;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public Ui () {
        this.in = new Scanner(System.in);
    }

    public String readCommand(String currentTripName) {
        if (currentTripName == null) {
            System.out.print("> ");
        } else {
            System.out.print("[Opened: " + currentTripName + "] > ");
        }
        String input = in.nextLine();
        return input.trim().toLowerCase();
    }

    public void showWelcomeMessage() {
        System.out.println("Welcome to \n" + LOGO);
        System.out.println("How can I help you plan today?");
        System.out.println("Commands: addtrip, listtrip, opentrip, deletetrip, exporttrip, importtrip, " +
                "addactivity, listactivity, editactivity, deleteactivity, addbudget, budgetsummary, " +
                "setexpense, listexpense, setcurrency, setdailylimit, help, exit");
    }

    public String promptField(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = in.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public String promptOptionalField(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }

    public int promptInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = in.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public double promptDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = in.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid decimal number.");
            }
        }
    }

    public String promptDate(String label) {
        while (true) {
            System.out.print(label + " (YYYY-MM-DD): ");
            String input = in.nextLine().trim();
            try {
                LocalDate.parse(input, DATE_FORMATTER);
                return input; // It's a valid date.
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD (e.g., 2026-12-25).");
            }
        }
    }

    public String promptTime(String label) {
        while (true) {
            System.out.print(label + " (HH:MM): ");
            String input = in.nextLine().trim();
            try {
                LocalTime.parse(input, TIME_FORMATTER);
                return input; // It's a valid time.
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use 24-hour HH:MM (e.g., 14:30).");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error. " + message);
    }

    public void printDivider(){
        System.out.println("===========================================================");
    }

    public void showMessageWithDivider(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }
}
