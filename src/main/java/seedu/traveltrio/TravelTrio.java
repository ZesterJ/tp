package seedu.traveltrio;

import seedu.traveltrio.command.trip.AddTripCommand;
import seedu.traveltrio.command.trip.DeleteTripCommand;
import seedu.traveltrio.command.trip.ListTripCommand;
import seedu.traveltrio.command.trip.OpenTripCommand;
import seedu.traveltrio.command.activity.AddActivityCommand;
import seedu.traveltrio.command.activity.ListActivityCommand;
import seedu.traveltrio.command.activity.DeleteActivityCommand;
import seedu.traveltrio.command.activity.EditActivityCommand;
import seedu.traveltrio.command.budget.AddBudgetCommand;
import seedu.traveltrio.command.budget.BudgetSummaryCommand;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import java.util.Scanner;

public class TravelTrio {
    private static final TripList tripList = new TripList();
    private static Trip openTrip = null;
    private static final Scanner in = new Scanner(System.in);
    private static final String LOGO =
            "  _______                   _ _______   _ \n"
            + " |__   __|                 | |__   __| (_) \n"
            + "    | |_ __ __ ___   _____ | |  | |_ __ _  ___  \n"
            + "    | | '__/ _` \\ \\ / / _ \\| |  | | '__| |/ _ \\ \n"
            + "    | | | | (_| |\\ V /  __/| |  | | |  | | (_) |\n"
            + "    |_|_|  \\__,_| \\_/ \\___||_|  |_|_|  |_|\\___/ \n";

    public static void main(String[] args) {
        System.out.println("Welcome to \n" + LOGO);
        System.out.println("How can I help you plan today?");
        System.out.println("Commands: addtrip, listtrip, opentrip, deletetrip, "
                + "addactivity, listactivity, editactivity, deleteactivity, addbudget, budgetsummary, exit");

        while (true) {
            System.out.println("> ");
            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye! Happy Travels!");
                break;
            }

            try {
                String command = input.toLowerCase();

                switch (command) {
                case "addtrip":
                    String name = promptField("Trip Name");
                    String start = promptField("Start Date (YYYY-MM-DD)");
                    String end = promptField("End Date (YYYY-MM-DD)");
                    System.out.println(new AddTripCommand(tripList, name, start, end).execute());
                    break;

                case "listtrip":
                    System.out.println(new ListTripCommand(tripList).execute());
                    break;

                case "opentrip":
                    System.out.println(new ListTripCommand(tripList).execute());
                    int idx = promptInt("Enter the number of the trip to open");
                    openTrip = tripList.get(idx - 1);
                    assert openTrip != null;
                    System.out.println(new OpenTripCommand(tripList, idx).execute());
                    break;

                case "deletetrip":
                    System.out.println(new ListTripCommand(tripList).execute());
                    int tripIdx = promptInt("Enter the number of the trip to delete");
                    System.out.println(new DeleteTripCommand(tripList, tripIdx).execute());
                    // If the open trip is the one deleted, reset opentrip
                    if (openTrip != null && !tripList.contains(openTrip)) {
                        openTrip = null;
                        System.out.println("The active trip was deleted. Use 'opentrip' to open a trip.");
                    }
                    break;

                case "addactivity":
                    ensureTripOpen();
                    String title = promptField("Activity Title");
                    String location = promptField("Location");
                    String date = promptField("Date (YYYY-MM-DD)");
                    String startTime = promptField("Start Time (HH:MM)");
                    String endTime = promptField("End Time (HH:MM)");
                    System.out.println(new AddActivityCommand(openTrip.getActivities(),
                            title, location, date, startTime, endTime)
                            .execute(openTrip.getName()));
                    break;

                case "listactivity":
                    ensureTripOpen();
                    System.out.println(new ListActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
                    break;

                case "editactivity":
                    ensureTripOpen();
                    System.out.println(new ListActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
                    int activityIdx = promptInt("Enter the number of the activity to edit");
                    System.out.println("Leave any field blank to keep current values.");
                    String newTitle = promptField("New Title");
                    String newLocation = promptField("New Location");
                    String newDate = promptField("New Date (YYYY-MM-DD)");
                    String newStartTime = promptField("New Start Time (HH:MM)");
                    String newEndTime = promptField("New End Time (HH:MM)");
                    System.out.println(new EditActivityCommand(openTrip.getActivities(),
                            activityIdx, newTitle, newLocation, newDate, newStartTime, newEndTime)
                            .execute(openTrip.getName()));

                    break;

                case "deleteactivity":
                    ensureTripOpen();
                    System.out.println(new ListActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
                    int actIdx = promptInt("Enter the number of the activity to delete");
                    System.out.println(new DeleteActivityCommand(openTrip.getActivities(), actIdx)
                            .execute(openTrip.getName()));
                    break;
                
                case "addbudget":
                    ensureTripOpen();
                    if (openTrip.getActivities().isEmpty()) {
                        System.out.println("No activities found. Please add an activity before setting a budget.");
                        break;
                    }
                    System.out.println(new ListActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
                    int budgetActivityIdx = promptInt("Enter the number of the activity to add a budget for. ");
                    double budgetAmount = promptDouble("Enter budget amount ($)");
                    System.out.println(new AddBudgetCommand(openTrip.getBudgets(),
                            openTrip.getActivities(), openTrip.getActivities().get(budgetActivityIdx - 1), budgetAmount)
                            .execute());
                    break;

                case "budgetsummary":
                    ensureTripOpen();
                    System.out.println(new BudgetSummaryCommand(openTrip.getBudgets(),
                            openTrip.getActivities()).execute());
                    break;
                default:
                    System.out.println("Unknown command. Available commands: addtrip, listtrip, "
                            + "opentrip, deletetrip, addactivity, listactivity, "
                            + "editactivity, deleteactivity, addbudget, budgetsummary, exit"); 
                }
            } catch (Exception e){
                System.out.println("Error. " + e.getMessage());
            }

        }

    }

    private static String promptField(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = in.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }

    }

    private static int promptInt(String label) {
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

    private static double promptDouble(String label) {
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

    private static void ensureTripOpen() {
        if (TravelTrio.openTrip == null) {
            throw new IllegalStateException("You need to open a trip first. (Use 'opentrip')");
        }
        assert openTrip != null : "openTrip should not be null after check";
    }
}


