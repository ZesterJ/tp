package seedu.traveltrio;

import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Handles loading and saving trip data to a local file.
 * File stores all trip, activity and budget details.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the list of trips and activities from the txt file from the previous session.
     *
     * @return A populated TripList or an empty one if no file exists.
     * @throws TravelTrioException if unable to read file.
     */
    public TripList load() throws TravelTrioException {
        TripList trips = new TripList();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return trips;
            }

            Scanner fileScanner = new Scanner(file);
            Trip currentTrip = null;
            Activity lastActivity = null;
            String currentDate = "";

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("***") || line.startsWith("---")) {
                    // Skip divider lines
                    continue;
                }

                if (line.startsWith("Trip:")) {
                    currentTrip = loadTripDetails(line, trips);
                } else if (line.startsWith("=== Date:")) {
                    currentDate = loadDates(line);
                } else if (line.startsWith("Activity")) {
                    assert currentTrip != null: "No trip open!";
                    lastActivity = loadActivityDetails(fileScanner, currentDate, currentTrip);
                } else if (line.contains("Budget set:")) {
                    assert currentTrip != null: "No trip open!";
                    loadBudgetDetails(lastActivity, line, fileScanner, currentTrip);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load trips from " + filePath, e);
            throw new TravelTrioException("Error reading file. File might be corrupted.");
        }
        return trips;
    }

    private static void loadBudgetDetails(Activity lastActivity, String line, Scanner fileScanner, Trip currentTrip) throws TravelTrioException {
        assert lastActivity != null : "Storage Error: " +
                "Budget data found without a preceding Activity block.";

        double total = Double.parseDouble(line.split(": ")[1].trim());
        String expenseLine = fileScanner.nextLine(); // Read the next line for Actual Expense
        double spent = Double.parseDouble(expenseLine.split(": ")[1].trim());

        Budget budget = new Budget(total, lastActivity);
        budget.setExpense(spent);

        currentTrip.getBudgets().addBudget(lastActivity, budget);
    }

    private static Activity loadActivityDetails(Scanner fileScanner, String currentDate, Trip currentTrip) throws TravelTrioException {
        Activity lastActivity;
        String title = fileScanner.nextLine().split(": ")[1].trim();
        String loc = fileScanner.nextLine().split(": ")[1].trim();
        String start = fileScanner.nextLine().split(": ")[1].trim();
        String end = fileScanner.nextLine().split(": ")[1].trim();

        lastActivity = new Activity(title, loc, currentDate, start, end);
        currentTrip.getActivities().add(lastActivity);
        return lastActivity;
    }

    private static String loadDates(String line) {
        String currentDate;
        currentDate = line.replace("===", "").replace("Date:", "").trim();
        return currentDate;
    }

    private static Trip loadTripDetails(String line, TripList trips) {
        Trip currentTrip;
        String[] parts = line.split(" \\| ");
        String name = parts[0].substring(parts[0].indexOf(":") + 1).trim();
        String start = parts[1].substring(parts[1].indexOf(":") + 1).trim();
        String end = parts[2].substring(parts[2].indexOf(":") + 1).trim();
        currentTrip = new Trip(name, start, end);
        trips.add(currentTrip);
        return currentTrip;
    }

    /**
     * Saves the current list of trips to the file.
     */
    public void save(TripList trips) throws TravelTrioException {
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();

            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < trips.size(); i++) {
                // Handles the T, A, and B lines
                writer.write(trips.get(i).toFileFormat());

                if (i < trips.size() - 1) {
                    writer.write("\n\n\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to save trips. " + e);
            throw new TravelTrioException("Data not saved. Please check if the folder is read-only.");
        }
    }
}
