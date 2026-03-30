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
    private static final Ui ui = new Ui();

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
        File file = new File(filePath);
        if (!file.exists()) {
            return new TripList();
        }
        return parseFileToTripList(file);
    }

    public Trip importTrip(String fileName) throws TravelTrioException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new TravelTrioException("File '" + fileName + "' not found. Make sure it is in the same folder.");
        }

        TripList importedTrips = parseFileToTripList(file);

        if (importedTrips.isEmpty()) {
            throw new TravelTrioException("No valid trip data found in " + fileName);
        }

        return importedTrips.get(0); // Return the first trip found in the shared file
    }

    public void exportTrip(Trip trip, String fileName) throws TravelTrioException {
        try {
            File file = new File(fileName);
            // Append .txt if the user forgot it
            if (!fileName.endsWith(".txt")) {
                file = new File(fileName + ".txt");
            }

            FileWriter writer = new FileWriter(file);
            writer.write(trip.toFileFormat());
            writer.close();

        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to export trip. " + e);
            throw new TravelTrioException("Failed to export trip to " + fileName + ".");
        }
    }

    private TripList parseFileToTripList(File file) throws TravelTrioException {
        TripList trips = new TripList();
        try (Scanner fileScanner = new Scanner(file)) {
            Trip currentTrip = null;
            Activity lastActivity = null;
            String currentDate = "";

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("***") || line.startsWith("---")) {
                    continue;
                }

                if (line.startsWith("Trip:")) {
                    currentTrip = loadTripDetails(fileScanner, line, trips);
                } else if (line.startsWith("=== Date:")) {
                    currentDate = loadDates(line);
                } else if (line.startsWith("Title:")) {
                    if (currentTrip == null) {
                        throw new TravelTrioException("Activity found without Trip header.");
                    }
                    lastActivity = loadActivityDetails(fileScanner, line, currentDate, currentTrip);
                } else if (line.contains("Budget set:")) {
                    assert currentTrip != null: "No trip open!";
                    loadBudgetDetails(lastActivity, line, fileScanner, currentTrip);
                } else if (!line.startsWith("Total Budget:")){
                    ui.showError("Line wrongly formatted found. [" + line + "]. Check formatting.");
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load trips from " + file.getPath(), e);
            throw new TravelTrioException("Error reading file. File might be corrupted.");
        }
        return trips;
    }

    private static void loadBudgetDetails(Activity lastActivity, String line, Scanner fileScanner, Trip currentTrip)
            throws TravelTrioException {
        assert lastActivity != null : "Storage Error: " +
                "Budget data found without a preceding Activity block.";

        double total = Double.parseDouble(line.split(": ")[1].trim());
        String expenseLine = fileScanner.nextLine(); // Read the next line for Actual Expense
        double spent = Double.parseDouble(expenseLine.split(": ")[1].trim());

        Budget budget = new Budget(total, lastActivity);
        budget.setActualExpense(spent);

        currentTrip.getBudgets().addBudget(lastActivity, budget);
    }

    private static Activity loadActivityDetails(Scanner fileScanner, String titleLine, String currentDate,
                                                Trip currentTrip) throws TravelTrioException {
        String title = titleLine.split(": ")[1].trim();
        String loc = fileScanner.nextLine().split(": ")[1].trim();
        String start = fileScanner.nextLine().split(": ")[1].trim();
        String end = fileScanner.nextLine().split(": ")[1].trim();

        Activity lastActivity = new Activity(title, loc, currentDate, start, end);
        currentTrip.getActivities().add(lastActivity);
        return lastActivity;
    }

    private static String loadDates(String line) {
        String currentDate;
        currentDate = line.replace("===", "").replace("Date:", "").trim();
        return currentDate;
    }

    private static Trip loadTripDetails(Scanner fileScanner, String line, TripList trips) {
        Trip currentTrip;
        String[] parts = line.split(" \\| ");
        String name = parts[0].substring(parts[0].indexOf(":") + 1).trim();
        String start = parts[1].substring(parts[1].indexOf(":") + 1).trim();
        String end = parts[2].substring(parts[2].indexOf(":") + 1).trim();
        currentTrip = new Trip(name, start, end);
        trips.add(currentTrip);

        // Read the next line for budget and exchange rate details
        if (fileScanner.hasNext()) {
            String budgetLine = fileScanner.nextLine().trim();
            if (budgetLine.contains("Exchange Rate:")) {
                String[] budgetParts = budgetLine.split(" \\| ");
                for (String part : budgetParts) {
                    if (part.contains("Exchange Rate:")) {
                        double exchangeRate = Double.parseDouble(part.split(": ")[1].trim());
                        currentTrip.getBudgets().setExchangeRate(exchangeRate);
                        break;
                    }
                }
            }
        }
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
