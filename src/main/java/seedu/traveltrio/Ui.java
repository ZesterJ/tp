package seedu.traveltrio;

import java.util.Scanner;

public class Ui {
    public static final String LOGO =
            "  _______                   _ _______   _ \n"
            + " |__   __|                 | |__   __| (_) \n"
            + "    | |_ __ __ ___   _____ | |  | |_ __ _  ___  \n"
            + "    | | '__/ _` \\ \\ / / _ \\| |  | | '__| |/ _ \\ \n"
            + "    | | | | (_| |\\ V /  __/| |  | | |  | | (_) |\n"
            + "    |_|_|  \\__,_| \\_/ \\___||_|  |_|_|  |_|\\___/ \n";

    private final Scanner in;

    public Ui () {
        this.in = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.println("> ");
        return in.nextLine().trim().toLowerCase();
    }

    public void showWelcomeMessage() {
        System.out.println("Welcome to \n" + LOGO);
        System.out.println("How can I help you plan today?");
        System.out.println("Commands: addtrip, listtrip, opentrip, deletetrip, "
                + "addactivity, listactivity, editactivity, deleteactivity, addbudget, " +
                "budgetsummary, setexpense, listexpense, setdailylimit, help, exit");
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
