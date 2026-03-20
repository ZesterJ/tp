package seedu.traveltrio;

import seedu.traveltrio.command.AddTripCommand;
import seedu.traveltrio.command.ListTripCommand;
import seedu.traveltrio.command.DeleteTripCommand;
import seedu.traveltrio.command.OpenTripCommand;
import seedu.traveltrio.command.AddActivityCommand;
import seedu.traveltrio.command.ListActivityCommand;
import seedu.traveltrio.command.DeleteActivityCommand;
import seedu.traveltrio.command.EditActivityCommand;

import seedu.traveltrio.model.Trip;
import seedu.traveltrio.model.TripList;

import java.util.HashMap;
import java.util.Scanner;

public class TravelTrio {
    private static final TripList tripList = new TripList();
    private static Trip openTrip = null;
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

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("> ");
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String[] parts = input.split(" ", 2);
                String command = parts[0].toLowerCase();
                String details = "";
                if (parts.length > 1) {
                    details = parts[1];
                }

                switch (command) {
                case "addtrip":
                    HashMap<String, String> tripMap = Parser.parseArgs(details, "n/", "s/", "e/");
                    String name = tripMap.get("n/");
                    String start = tripMap.get("s/");
                    String end = tripMap.get("e/");

                    System.out.println(new AddTripCommand(tripList, name, start, end).execute());
                    break;

                case "listtrip":
                    System.out.println(new ListTripCommand(tripList).execute());
                    break;

                case "opentrip":
                    int idx = Integer.parseInt(details);
                    openTrip = tripList.get(idx - 1);
                    System.out.println(new OpenTripCommand(tripList, idx).execute());
                    break;

                case "deletetrip":
                    int tripIdx = Integer.parseInt(details);
                    System.out.println(new DeleteTripCommand(tripList, tripIdx).execute());
                    // If the open trip is the one deleted, reset opentrip
                    if (openTrip != null && !tripList.contains(openTrip)) {
                        openTrip = null;
                        System.out.println("The active trip was deleted. Use 'opentrip' to open a trip.");
                    }
                    break;

                case "addactivity":
                    ensureTripOpen();
                    HashMap<String, String> activityMap = Parser.parseArgs(details, "t/", "d/", "st/", "et/", "l/");
                    String title = activityMap.get("t/");
                    String date = activityMap.get("d/");
                    String startTime = activityMap.get("st/");
                    String endTime = activityMap.get("et/");
                    String location = activityMap.get("l/");

                    System.out.println(new AddActivityCommand(openTrip.getActivities(),
                            title, location, date, startTime, endTime)
                            .execute(openTrip.getName()));
                    break;

                case "listactivity":
                    ensureTripOpen();
                    System.out.println(new ListActivityCommand(openTrip.getActivities())
                            .execute(openTrip.getName()));
                    break;

                case "editactivity":
                    ensureTripOpen();
                    HashMap<String, String> editMap = Parser.parseArgs(details, "i/", "t/", "d/", "st/", "et/", "l/");
                    int activityIdx = Integer.parseInt(editMap.get("i/"));

                    System.out.println(new EditActivityCommand(openTrip.getActivities(), activityIdx,
                            editMap.get("t/"), editMap.get("l/"), editMap.get("d/"),
                            editMap.get("st/"), editMap.get("et/")).execute(openTrip.getName()));
                    break;

                case "deleteactivity":
                    ensureTripOpen();
                    int actIdx = Integer.parseInt(details);
                    System.out.println(new DeleteActivityCommand(openTrip.getActivities(), actIdx)
                            .execute(openTrip.getName()));
                    break;

                default:
                    System.out.println("Unknown command.");

                }
            } catch (Exception e){
                System.out.println("Error. " + e.getMessage());
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


